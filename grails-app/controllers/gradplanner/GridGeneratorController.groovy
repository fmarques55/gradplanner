package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class GridGeneratorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [gridGeneratorInstanceList: GridGenerator.list(params), gridGeneratorInstanceTotal: GridGenerator.count()]
    }

    def create() {
        [gridGeneratorInstance: new GridGenerator(params)]
    }

    def save() {
        def gridGeneratorInstance = new GridGenerator(params)
        if (!gridGeneratorInstance.save(flush: true)) {
            render(view: "create", model: [gridGeneratorInstance: gridGeneratorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), gridGeneratorInstance.id])
        redirect(action: "show", id: gridGeneratorInstance.id)
    }

    def show(Long id) {
        def gridGeneratorInstance = GridGenerator.get(id)
        if (!gridGeneratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), id])
            redirect(action: "list")
            return
        }

        [gridGeneratorInstance: gridGeneratorInstance]
    }

    def edit(Long id) {
        def gridGeneratorInstance = GridGenerator.get(id)
        if (!gridGeneratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), id])
            redirect(action: "list")
            return
        }

        [gridGeneratorInstance: gridGeneratorInstance]
    }

    def update(Long id, Long version) {
        def gridGeneratorInstance = GridGenerator.get(id)
        if (!gridGeneratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (gridGeneratorInstance.version > version) {
                gridGeneratorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'gridGenerator.label', default: 'GridGenerator')] as Object[],
                          "Another user has updated this GridGenerator while you were editing")
                render(view: "edit", model: [gridGeneratorInstance: gridGeneratorInstance])
                return
            }
        }

        gridGeneratorInstance.properties = params

        if (!gridGeneratorInstance.save(flush: true)) {
            render(view: "edit", model: [gridGeneratorInstance: gridGeneratorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), gridGeneratorInstance.id])
        redirect(action: "show", id: gridGeneratorInstance.id)
    }

    def delete(Long id) {
        def gridGeneratorInstance = GridGenerator.get(id)
        if (!gridGeneratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), id])
            redirect(action: "list")
            return
        }

        try {
            gridGeneratorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'gridGenerator.label', default: 'GridGenerator'), id])
            redirect(action: "show", id: id)
        }
    }
}
