package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(MentionController)
@Mock(Mention)
class MentionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/mention/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.mentionInstanceList.size() == 0
        assert model.mentionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.mentionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.mentionInstance != null
        assert view == '/mention/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/mention/show/1'
        assert controller.flash.message != null
        assert Mention.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/mention/list'

        populateValidParams(params)
        def mention = new Mention(params)

        assert mention.save() != null

        params.id = mention.id

        def model = controller.show()

        assert model.mentionInstance == mention
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/mention/list'

        populateValidParams(params)
        def mention = new Mention(params)

        assert mention.save() != null

        params.id = mention.id

        def model = controller.edit()

        assert model.mentionInstance == mention
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/mention/list'

        response.reset()

        populateValidParams(params)
        def mention = new Mention(params)

        assert mention.save() != null

        // test invalid parameters in update
        params.id = mention.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/mention/edit"
        assert model.mentionInstance != null

        mention.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/mention/show/$mention.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        mention.clearErrors()

        populateValidParams(params)
        params.id = mention.id
        params.version = -1
        controller.update()

        assert view == "/mention/edit"
        assert model.mentionInstance != null
        assert model.mentionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/mention/list'

        response.reset()

        populateValidParams(params)
        def mention = new Mention(params)

        assert mention.save() != null
        assert Mention.count() == 1

        params.id = mention.id

        controller.delete()

        assert Mention.count() == 0
        assert Mention.get(mention.id) == null
        assert response.redirectedUrl == '/mention/list'
    }
}
