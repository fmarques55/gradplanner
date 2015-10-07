package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class GridController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [gridInstanceList: Grid.list(params), gridInstanceTotal: Grid.count()]
    }

    def create() {
        [gridInstance: new Grid(params)]
    }

    def save() {
        def gridInstance = new Grid(params)
        if (!gridInstance.save(flush: true)) {
            render(view: "create", model: [gridInstance: gridInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'grid.label', default: 'Grid'), gridInstance.id])
        redirect(action: "show", id: gridInstance.id)
    }

    def show(Long id) {
        def gridInstance = Grid.get(id)
        if (!gridInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'grid.label', default: 'Grid'), id])
            redirect(action: "list")
            return
        }

        [gridInstance: gridInstance]
    }

    def edit(Long id) {
        def gridInstance = Grid.get(id)
        if (!gridInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'grid.label', default: 'Grid'), id])
            redirect(action: "list")
            return
        }

        [gridInstance: gridInstance]
    }

    def update(Long id, Long version) {
        def gridInstance = Grid.get(id)
        if (!gridInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'grid.label', default: 'Grid'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (gridInstance.version > version) {
                gridInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'grid.label', default: 'Grid')] as Object[],
                          "Another user has updated this Grid while you were editing")
                render(view: "edit", model: [gridInstance: gridInstance])
                return
            }
        }

        gridInstance.properties = params

        if (!gridInstance.save(flush: true)) {
            render(view: "edit", model: [gridInstance: gridInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'grid.label', default: 'Grid'), gridInstance.id])
        redirect(action: "show", id: gridInstance.id)
    }

    def delete(Long id) {
        def gridInstance = Grid.get(id)
        if (!gridInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'grid.label', default: 'Grid'), id])
            redirect(action: "list")
            return
        }

        try {
            gridInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'grid.label', default: 'Grid'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'grid.label', default: 'Grid'), id])
            redirect(action: "show", id: id)
        }
    }
}
