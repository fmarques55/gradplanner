package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(GridController)
@Mock(Grid)
class GridControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/grid/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.gridInstanceList.size() == 0
        assert model.gridInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.gridInstance != null
    }

    void testSave() {
        controller.save()

        assert model.gridInstance != null
        assert view == '/grid/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/grid/show/1'
        assert controller.flash.message != null
        assert Grid.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/grid/list'

        populateValidParams(params)
        def grid = new Grid(params)

        assert grid.save() != null

        params.id = grid.id

        def model = controller.show()

        assert model.gridInstance == grid
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/grid/list'

        populateValidParams(params)
        def grid = new Grid(params)

        assert grid.save() != null

        params.id = grid.id

        def model = controller.edit()

        assert model.gridInstance == grid
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/grid/list'

        response.reset()

        populateValidParams(params)
        def grid = new Grid(params)

        assert grid.save() != null

        // test invalid parameters in update
        params.id = grid.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/grid/edit"
        assert model.gridInstance != null

        grid.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/grid/show/$grid.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        grid.clearErrors()

        populateValidParams(params)
        params.id = grid.id
        params.version = -1
        controller.update()

        assert view == "/grid/edit"
        assert model.gridInstance != null
        assert model.gridInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/grid/list'

        response.reset()

        populateValidParams(params)
        def grid = new Grid(params)

        assert grid.save() != null
        assert Grid.count() == 1

        params.id = grid.id

        controller.delete()

        assert Grid.count() == 0
        assert Grid.get(grid.id) == null
        assert response.redirectedUrl == '/grid/list'
    }
}
