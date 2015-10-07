package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class ClassNameController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [classNameInstanceList: ClassName.list(params), classNameInstanceTotal: ClassName.count()]
    }

    def create() {
        [classNameInstance: new ClassName(params)]
    }

    def save() {
        def classNameInstance = new ClassName(params)
        if (!classNameInstance.save(flush: true)) {
            render(view: "create", model: [classNameInstance: classNameInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'className.label', default: 'ClassName'), classNameInstance.id])
        redirect(action: "show", id: classNameInstance.id)
    }

    def show(Long id) {
        def classNameInstance = ClassName.get(id)
        if (!classNameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'className.label', default: 'ClassName'), id])
            redirect(action: "list")
            return
        }

        [classNameInstance: classNameInstance]
    }

    def edit(Long id) {
        def classNameInstance = ClassName.get(id)
        if (!classNameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'className.label', default: 'ClassName'), id])
            redirect(action: "list")
            return
        }

        [classNameInstance: classNameInstance]
    }

    def update(Long id, Long version) {
        def classNameInstance = ClassName.get(id)
        if (!classNameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'className.label', default: 'ClassName'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (classNameInstance.version > version) {
                classNameInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'className.label', default: 'ClassName')] as Object[],
                          "Another user has updated this ClassName while you were editing")
                render(view: "edit", model: [classNameInstance: classNameInstance])
                return
            }
        }

        classNameInstance.properties = params

        if (!classNameInstance.save(flush: true)) {
            render(view: "edit", model: [classNameInstance: classNameInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'className.label', default: 'ClassName'), classNameInstance.id])
        redirect(action: "show", id: classNameInstance.id)
    }

    def delete(Long id) {
        def classNameInstance = ClassName.get(id)
        if (!classNameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'className.label', default: 'ClassName'), id])
            redirect(action: "list")
            return
        }

        try {
            classNameInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'className.label', default: 'ClassName'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'className.label', default: 'ClassName'), id])
            redirect(action: "show", id: id)
        }
    }
}
