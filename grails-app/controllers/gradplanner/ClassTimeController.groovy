package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class ClassTimeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [classTimeInstanceList: ClassTime.list(params), classTimeInstanceTotal: ClassTime.count()]
    }

    def create() {
        [classTimeInstance: new ClassTime(params)]
    }

    def save() {
        def classTimeInstance = new ClassTime(params)
        if (!classTimeInstance.save(flush: true)) {
            render(view: "create", model: [classTimeInstance: classTimeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'classTime.label', default: 'ClassTime'), classTimeInstance.id])
        redirect(action: "show", id: classTimeInstance.id)
    }

    def show(Long id) {
        def classTimeInstance = ClassTime.get(id)
        if (!classTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classTime.label', default: 'ClassTime'), id])
            redirect(action: "list")
            return
        }

        [classTimeInstance: classTimeInstance]
    }

    def edit(Long id) {
        def classTimeInstance = ClassTime.get(id)
        if (!classTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classTime.label', default: 'ClassTime'), id])
            redirect(action: "list")
            return
        }

        [classTimeInstance: classTimeInstance]
    }

    def update(Long id, Long version) {
        def classTimeInstance = ClassTime.get(id)
        if (!classTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classTime.label', default: 'ClassTime'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (classTimeInstance.version > version) {
                classTimeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'classTime.label', default: 'ClassTime')] as Object[],
                          "Another user has updated this ClassTime while you were editing")
                render(view: "edit", model: [classTimeInstance: classTimeInstance])
                return
            }
        }

        classTimeInstance.properties = params

        if (!classTimeInstance.save(flush: true)) {
            render(view: "edit", model: [classTimeInstance: classTimeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'classTime.label', default: 'ClassTime'), classTimeInstance.id])
        redirect(action: "show", id: classTimeInstance.id)
    }

    def delete(Long id) {
        def classTimeInstance = ClassTime.get(id)
        if (!classTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classTime.label', default: 'ClassTime'), id])
            redirect(action: "list")
            return
        }

        try {
            classTimeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'classTime.label', default: 'ClassTime'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'classTime.label', default: 'ClassTime'), id])
            redirect(action: "show", id: id)
        }
    }
}
