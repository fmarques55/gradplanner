package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class MatrixController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [matrixInstanceList: Matrix.list(params), matrixInstanceTotal: Matrix.count()]
    }

    def create() {
        [matrixInstance: new Matrix(params)]
    }

    def save() {
        def matrixInstance = new Matrix(params)
        if (!matrixInstance.save(flush: true)) {
            render(view: "create", model: [matrixInstance: matrixInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'matrix.label', default: 'Matrix'), matrixInstance.id])
        redirect(action: "show", id: matrixInstance.id)
    }

    def show(Long id) {
        def matrixInstance = Matrix.get(id)
        if (!matrixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'matrix.label', default: 'Matrix'), id])
            redirect(action: "list")
            return
        }

        [matrixInstance: matrixInstance]
    }

    def edit(Long id) {
        def matrixInstance = Matrix.get(id)
        if (!matrixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'matrix.label', default: 'Matrix'), id])
            redirect(action: "list")
            return
        }

        [matrixInstance: matrixInstance]
    }

    def update(Long id, Long version) {
        def matrixInstance = Matrix.get(id)
        if (!matrixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'matrix.label', default: 'Matrix'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (matrixInstance.version > version) {
                matrixInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'matrix.label', default: 'Matrix')] as Object[],
                          "Another user has updated this Matrix while you were editing")
                render(view: "edit", model: [matrixInstance: matrixInstance])
                return
            }
        }

        matrixInstance.properties = params

        if (!matrixInstance.save(flush: true)) {
            render(view: "edit", model: [matrixInstance: matrixInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'matrix.label', default: 'Matrix'), matrixInstance.id])
        redirect(action: "show", id: matrixInstance.id)
    }

    def delete(Long id) {
        def matrixInstance = Matrix.get(id)
        if (!matrixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'matrix.label', default: 'Matrix'), id])
            redirect(action: "list")
            return
        }

        try {
            matrixInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'matrix.label', default: 'Matrix'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'matrix.label', default: 'Matrix'), id])
            redirect(action: "show", id: id)
        }
    }
}
