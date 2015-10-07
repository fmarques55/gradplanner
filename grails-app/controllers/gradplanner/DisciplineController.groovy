package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class DisciplineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [disciplineInstanceList: Discipline.list(params), disciplineInstanceTotal: Discipline.count()]
    }

    def create() {
        [disciplineInstance: new Discipline(params)]
    }

    def save() {
        def disciplineInstance = new Discipline(params)
        if (!disciplineInstance.save(flush: true)) {
            render(view: "create", model: [disciplineInstance: disciplineInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'discipline.label', default: 'Discipline'), disciplineInstance.id])
        redirect(action: "show", id: disciplineInstance.id)
    }

    def show(Long id) {
        def disciplineInstance = Discipline.get(id)
        if (!disciplineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'discipline.label', default: 'Discipline'), id])
            redirect(action: "list")
            return
        }

        [disciplineInstance: disciplineInstance]
    }

    def edit(Long id) {
        def disciplineInstance = Discipline.get(id)
        if (!disciplineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'discipline.label', default: 'Discipline'), id])
            redirect(action: "list")
            return
        }

        [disciplineInstance: disciplineInstance]
    }

    def update(Long id, Long version) {
        def disciplineInstance = Discipline.get(id)
        if (!disciplineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'discipline.label', default: 'Discipline'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (disciplineInstance.version > version) {
                disciplineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'discipline.label', default: 'Discipline')] as Object[],
                          "Another user has updated this Discipline while you were editing")
                render(view: "edit", model: [disciplineInstance: disciplineInstance])
                return
            }
        }

        disciplineInstance.properties = params

        if (!disciplineInstance.save(flush: true)) {
            render(view: "edit", model: [disciplineInstance: disciplineInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'discipline.label', default: 'Discipline'), disciplineInstance.id])
        redirect(action: "show", id: disciplineInstance.id)
    }

    def delete(Long id) {
        def disciplineInstance = Discipline.get(id)
        if (!disciplineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'discipline.label', default: 'Discipline'), id])
            redirect(action: "list")
            return
        }

        try {
            disciplineInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'discipline.label', default: 'Discipline'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'discipline.label', default: 'Discipline'), id])
            redirect(action: "show", id: id)
        }
    }
}
