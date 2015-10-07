package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class MentionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [mentionInstanceList: Mention.list(params), mentionInstanceTotal: Mention.count()]
    }

    def create() {
        [mentionInstance: new Mention(params)]
    }

    def save() {
        def mentionInstance = new Mention(params)
        if (!mentionInstance.save(flush: true)) {
            render(view: "create", model: [mentionInstance: mentionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'mention.label', default: 'Mention'), mentionInstance.id])
        redirect(action: "show", id: mentionInstance.id)
    }

    def show(Long id) {
        def mentionInstance = Mention.get(id)
        if (!mentionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mention.label', default: 'Mention'), id])
            redirect(action: "list")
            return
        }

        [mentionInstance: mentionInstance]
    }

    def edit(Long id) {
        def mentionInstance = Mention.get(id)
        if (!mentionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mention.label', default: 'Mention'), id])
            redirect(action: "list")
            return
        }

        [mentionInstance: mentionInstance]
    }

    def update(Long id, Long version) {
        def mentionInstance = Mention.get(id)
        if (!mentionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mention.label', default: 'Mention'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (mentionInstance.version > version) {
                mentionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'mention.label', default: 'Mention')] as Object[],
                          "Another user has updated this Mention while you were editing")
                render(view: "edit", model: [mentionInstance: mentionInstance])
                return
            }
        }

        mentionInstance.properties = params

        if (!mentionInstance.save(flush: true)) {
            render(view: "edit", model: [mentionInstance: mentionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'mention.label', default: 'Mention'), mentionInstance.id])
        redirect(action: "show", id: mentionInstance.id)
    }

    def delete(Long id) {
        def mentionInstance = Mention.get(id)
        if (!mentionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mention.label', default: 'Mention'), id])
            redirect(action: "list")
            return
        }

        try {
            mentionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'mention.label', default: 'Mention'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'mention.label', default: 'Mention'), id])
            redirect(action: "show", id: id)
        }
    }
}
