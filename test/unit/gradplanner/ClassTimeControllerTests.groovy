package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(ClassTimeController)
@Mock(ClassTime)
class ClassTimeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/classTime/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.classTimeInstanceList.size() == 0
        assert model.classTimeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.classTimeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.classTimeInstance != null
        assert view == '/classTime/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/classTime/show/1'
        assert controller.flash.message != null
        assert ClassTime.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/classTime/list'

        populateValidParams(params)
        def classTime = new ClassTime(params)

        assert classTime.save() != null

        params.id = classTime.id

        def model = controller.show()

        assert model.classTimeInstance == classTime
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/classTime/list'

        populateValidParams(params)
        def classTime = new ClassTime(params)

        assert classTime.save() != null

        params.id = classTime.id

        def model = controller.edit()

        assert model.classTimeInstance == classTime
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/classTime/list'

        response.reset()

        populateValidParams(params)
        def classTime = new ClassTime(params)

        assert classTime.save() != null

        // test invalid parameters in update
        params.id = classTime.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/classTime/edit"
        assert model.classTimeInstance != null

        classTime.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/classTime/show/$classTime.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        classTime.clearErrors()

        populateValidParams(params)
        params.id = classTime.id
        params.version = -1
        controller.update()

        assert view == "/classTime/edit"
        assert model.classTimeInstance != null
        assert model.classTimeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/classTime/list'

        response.reset()

        populateValidParams(params)
        def classTime = new ClassTime(params)

        assert classTime.save() != null
        assert ClassTime.count() == 1

        params.id = classTime.id

        controller.delete()

        assert ClassTime.count() == 0
        assert ClassTime.get(classTime.id) == null
        assert response.redirectedUrl == '/classTime/list'
    }
}
