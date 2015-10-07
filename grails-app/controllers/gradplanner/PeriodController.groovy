package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class PeriodController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [periodInstanceList: Period.list(params), periodInstanceTotal: Period.count()]
    }

    def create() {
        [periodInstance: new Period(params)]
    }

    def save() {
        def periodInstance = new Period(params)
        if (!periodInstance.save(flush: true)) {
            render(view: "create", model: [periodInstance: periodInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'period.label', default: 'Period'), periodInstance.id])
        redirect(action: "show", id: periodInstance.id)
    }

    def show(Long id) {
        def periodInstance = Period.get(id)
        if (!periodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'period.label', default: 'Period'), id])
            redirect(action: "list")
            return
        }

        [periodInstance: periodInstance]
    }

    def edit(Long id) {
        def periodInstance = Period.get(id)
        if (!periodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'period.label', default: 'Period'), id])
            redirect(action: "list")
            return
        }

        [periodInstance: periodInstance]
    }

    def update(Long id, Long version) {
        def periodInstance = Period.get(id)
        if (!periodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'period.label', default: 'Period'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (periodInstance.version > version) {
                periodInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'period.label', default: 'Period')] as Object[],
                          "Another user has updated this Period while you were editing")
                render(view: "edit", model: [periodInstance: periodInstance])
                return
            }
        }

        periodInstance.properties = params

        if (!periodInstance.save(flush: true)) {
            render(view: "edit", model: [periodInstance: periodInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'period.label', default: 'Period'), periodInstance.id])
        redirect(action: "show", id: periodInstance.id)
    }

    def delete(Long id) {
        def periodInstance = Period.get(id)
        if (!periodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'period.label', default: 'Period'), id])
            redirect(action: "list")
            return
        }

        try {
            periodInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'period.label', default: 'Period'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'period.label', default: 'Period'), id])
            redirect(action: "show", id: id)
        }
    }
}
