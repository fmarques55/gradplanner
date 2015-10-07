package gradplanner



import org.junit.*
import grails.test.mixin.*

@TestFor(DisciplineController)
@Mock(Discipline)
class DisciplineControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/discipline/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.disciplineInstanceList.size() == 0
        assert model.disciplineInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.disciplineInstance != null
    }

    void testSave() {
        controller.save()

        assert model.disciplineInstance != null
        assert view == '/discipline/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/discipline/show/1'
        assert controller.flash.message != null
        assert Discipline.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/discipline/list'

        populateValidParams(params)
        def discipline = new Discipline(params)

        assert discipline.save() != null

        params.id = discipline.id

        def model = controller.show()

        assert model.disciplineInstance == discipline
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/discipline/list'

        populateValidParams(params)
        def discipline = new Discipline(params)

        assert discipline.save() != null

        params.id = discipline.id

        def model = controller.edit()

        assert model.disciplineInstance == discipline
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/discipline/list'

        response.reset()

        populateValidParams(params)
        def discipline = new Discipline(params)

        assert discipline.save() != null

        // test invalid parameters in update
        params.id = discipline.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/discipline/edit"
        assert model.disciplineInstance != null

        discipline.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/discipline/show/$discipline.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        discipline.clearErrors()

        populateValidParams(params)
        params.id = discipline.id
        params.version = -1
        controller.update()

        assert view == "/discipline/edit"
        assert model.disciplineInstance != null
        assert model.disciplineInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/discipline/list'

        response.reset()

        populateValidParams(params)
        def discipline = new Discipline(params)

        assert discipline.save() != null
        assert Discipline.count() == 1

        params.id = discipline.id

        controller.delete()

        assert Discipline.count() == 0
        assert Discipline.get(discipline.id) == null
        assert response.redirectedUrl == '/discipline/list'
    }
}
