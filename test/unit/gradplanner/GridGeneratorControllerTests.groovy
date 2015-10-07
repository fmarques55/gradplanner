package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(GridGeneratorController)
@Mock(GridGenerator)
class GridGeneratorControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/gridGenerator/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.gridGeneratorInstanceList.size() == 0
        assert model.gridGeneratorInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.gridGeneratorInstance != null
    }

    void testSave() {
        controller.save()

        assert model.gridGeneratorInstance != null
        assert view == '/gridGenerator/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/gridGenerator/show/1'
        assert controller.flash.message != null
        assert GridGenerator.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/gridGenerator/list'

        populateValidParams(params)
        def gridGenerator = new GridGenerator(params)

        assert gridGenerator.save() != null

        params.id = gridGenerator.id

        def model = controller.show()

        assert model.gridGeneratorInstance == gridGenerator
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/gridGenerator/list'

        populateValidParams(params)
        def gridGenerator = new GridGenerator(params)

        assert gridGenerator.save() != null

        params.id = gridGenerator.id

        def model = controller.edit()

        assert model.gridGeneratorInstance == gridGenerator
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/gridGenerator/list'

        response.reset()

        populateValidParams(params)
        def gridGenerator = new GridGenerator(params)

        assert gridGenerator.save() != null

        // test invalid parameters in update
        params.id = gridGenerator.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/gridGenerator/edit"
        assert model.gridGeneratorInstance != null

        gridGenerator.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/gridGenerator/show/$gridGenerator.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        gridGenerator.clearErrors()

        populateValidParams(params)
        params.id = gridGenerator.id
        params.version = -1
        controller.update()

        assert view == "/gridGenerator/edit"
        assert model.gridGeneratorInstance != null
        assert model.gridGeneratorInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/gridGenerator/list'

        response.reset()

        populateValidParams(params)
        def gridGenerator = new GridGenerator(params)

        assert gridGenerator.save() != null
        assert GridGenerator.count() == 1

        params.id = gridGenerator.id

        controller.delete()

        assert GridGenerator.count() == 0
        assert GridGenerator.get(gridGenerator.id) == null
        assert response.redirectedUrl == '/gridGenerator/list'
    }
}
