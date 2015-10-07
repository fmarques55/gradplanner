package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(ClassNameController)
@Mock(ClassName)
class ClassNameControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/className/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.classNameInstanceList.size() == 0
        assert model.classNameInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.classNameInstance != null
    }

    void testSave() {
        controller.save()

        assert model.classNameInstance != null
        assert view == '/className/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/className/show/1'
        assert controller.flash.message != null
        assert ClassName.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/className/list'

        populateValidParams(params)
        def className = new ClassName(params)

        assert className.save() != null

        params.id = className.id

        def model = controller.show()

        assert model.classNameInstance == className
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/className/list'

        populateValidParams(params)
        def className = new ClassName(params)

        assert className.save() != null

        params.id = className.id

        def model = controller.edit()

        assert model.classNameInstance == className
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/className/list'

        response.reset()

        populateValidParams(params)
        def className = new ClassName(params)

        assert className.save() != null

        // test invalid parameters in update
        params.id = className.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/className/edit"
        assert model.classNameInstance != null

        className.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/className/show/$className.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        className.clearErrors()

        populateValidParams(params)
        params.id = className.id
        params.version = -1
        controller.update()

        assert view == "/className/edit"
        assert model.classNameInstance != null
        assert model.classNameInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/className/list'

        response.reset()

        populateValidParams(params)
        def className = new ClassName(params)

        assert className.save() != null
        assert ClassName.count() == 1

        params.id = className.id

        controller.delete()

        assert ClassName.count() == 0
        assert ClassName.get(className.id) == null
        assert response.redirectedUrl == '/className/list'
    }
}
