package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(TranscriptOfRecordsController)
@Mock(TranscriptOfRecords)
class TranscriptOfRecordsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/transcriptOfRecords/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.transcriptOfRecordsInstanceList.size() == 0
        assert model.transcriptOfRecordsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.transcriptOfRecordsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.transcriptOfRecordsInstance != null
        assert view == '/transcriptOfRecords/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/transcriptOfRecords/show/1'
        assert controller.flash.message != null
        assert TranscriptOfRecords.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/transcriptOfRecords/list'

        populateValidParams(params)
        def transcriptOfRecords = new TranscriptOfRecords(params)

        assert transcriptOfRecords.save() != null

        params.id = transcriptOfRecords.id

        def model = controller.show()

        assert model.transcriptOfRecordsInstance == transcriptOfRecords
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/transcriptOfRecords/list'

        populateValidParams(params)
        def transcriptOfRecords = new TranscriptOfRecords(params)

        assert transcriptOfRecords.save() != null

        params.id = transcriptOfRecords.id

        def model = controller.edit()

        assert model.transcriptOfRecordsInstance == transcriptOfRecords
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/transcriptOfRecords/list'

        response.reset()

        populateValidParams(params)
        def transcriptOfRecords = new TranscriptOfRecords(params)

        assert transcriptOfRecords.save() != null

        // test invalid parameters in update
        params.id = transcriptOfRecords.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/transcriptOfRecords/edit"
        assert model.transcriptOfRecordsInstance != null

        transcriptOfRecords.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/transcriptOfRecords/show/$transcriptOfRecords.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        transcriptOfRecords.clearErrors()

        populateValidParams(params)
        params.id = transcriptOfRecords.id
        params.version = -1
        controller.update()

        assert view == "/transcriptOfRecords/edit"
        assert model.transcriptOfRecordsInstance != null
        assert model.transcriptOfRecordsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/transcriptOfRecords/list'

        response.reset()

        populateValidParams(params)
        def transcriptOfRecords = new TranscriptOfRecords(params)

        assert transcriptOfRecords.save() != null
        assert TranscriptOfRecords.count() == 1

        params.id = transcriptOfRecords.id

        controller.delete()

        assert TranscriptOfRecords.count() == 0
        assert TranscriptOfRecords.get(transcriptOfRecords.id) == null
        assert response.redirectedUrl == '/transcriptOfRecords/list'
    }
}
