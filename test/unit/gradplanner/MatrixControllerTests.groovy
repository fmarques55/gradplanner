package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(MatrixController)
@Mock(Matrix)
class MatrixControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/matrix/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.matrixInstanceList.size() == 0
        assert model.matrixInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.matrixInstance != null
    }

    void testSave() {
        controller.save()

        assert model.matrixInstance != null
        assert view == '/matrix/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/matrix/show/1'
        assert controller.flash.message != null
        assert Matrix.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/matrix/list'

        populateValidParams(params)
        def matrix = new Matrix(params)

        assert matrix.save() != null

        params.id = matrix.id

        def model = controller.show()

        assert model.matrixInstance == matrix
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/matrix/list'

        populateValidParams(params)
        def matrix = new Matrix(params)

        assert matrix.save() != null

        params.id = matrix.id

        def model = controller.edit()

        assert model.matrixInstance == matrix
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/matrix/list'

        response.reset()

        populateValidParams(params)
        def matrix = new Matrix(params)

        assert matrix.save() != null

        // test invalid parameters in update
        params.id = matrix.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/matrix/edit"
        assert model.matrixInstance != null

        matrix.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/matrix/show/$matrix.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        matrix.clearErrors()

        populateValidParams(params)
        params.id = matrix.id
        params.version = -1
        controller.update()

        assert view == "/matrix/edit"
        assert model.matrixInstance != null
        assert model.matrixInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/matrix/list'

        response.reset()

        populateValidParams(params)
        def matrix = new Matrix(params)

        assert matrix.save() != null
        assert Matrix.count() == 1

        params.id = matrix.id

        controller.delete()

        assert Matrix.count() == 0
        assert Matrix.get(matrix.id) == null
        assert response.redirectedUrl == '/matrix/list'
    }
}
