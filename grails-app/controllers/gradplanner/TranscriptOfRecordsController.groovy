package gradplanner

import org.springframework.dao.DataIntegrityViolationException

class TranscriptOfRecordsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [transcriptOfRecordsInstanceList: TranscriptOfRecords.list(params), transcriptOfRecordsInstanceTotal: TranscriptOfRecords.count()]
    }

    def create() {
        [transcriptOfRecordsInstance: new TranscriptOfRecords(params)]
    }

    def save() {
        def transcriptOfRecordsInstance = new TranscriptOfRecords(params)
        if (!transcriptOfRecordsInstance.save(flush: true)) {
            render(view: "create", model: [transcriptOfRecordsInstance: transcriptOfRecordsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), transcriptOfRecordsInstance.id])
        redirect(action: "show", id: transcriptOfRecordsInstance.id)
    }

    def show(Long id) {
        def transcriptOfRecordsInstance = TranscriptOfRecords.get(id)
        if (!transcriptOfRecordsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), id])
            redirect(action: "list")
            return
        }

        [transcriptOfRecordsInstance: transcriptOfRecordsInstance]
    }

    def edit(Long id) {
        def transcriptOfRecordsInstance = TranscriptOfRecords.get(id)
        if (!transcriptOfRecordsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), id])
            redirect(action: "list")
            return
        }

        [transcriptOfRecordsInstance: transcriptOfRecordsInstance]
    }

    def update(Long id, Long version) {
        def transcriptOfRecordsInstance = TranscriptOfRecords.get(id)
        if (!transcriptOfRecordsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (transcriptOfRecordsInstance.version > version) {
                transcriptOfRecordsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords')] as Object[],
                          "Another user has updated this TranscriptOfRecords while you were editing")
                render(view: "edit", model: [transcriptOfRecordsInstance: transcriptOfRecordsInstance])
                return
            }
        }

        transcriptOfRecordsInstance.properties = params

        if (!transcriptOfRecordsInstance.save(flush: true)) {
            render(view: "edit", model: [transcriptOfRecordsInstance: transcriptOfRecordsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), transcriptOfRecordsInstance.id])
        redirect(action: "show", id: transcriptOfRecordsInstance.id)
    }

    def delete(Long id) {
        def transcriptOfRecordsInstance = TranscriptOfRecords.get(id)
        if (!transcriptOfRecordsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), id])
            redirect(action: "list")
            return
        }

        try {
            transcriptOfRecordsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords'), id])
            redirect(action: "show", id: id)
        }
    }
}
