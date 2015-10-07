import gradplanner.*

class BootStrap {

    def init = { servletContext ->
    	instantiateUsers()

    }
    def destroy = {
    }

    private void instantiateUsers()
    {
    	//Defining roles
    	def commonUser = new Role(authority: "ROLE_USER").save(flush:true, failOnError:true).save()

    	def testUser = new User(username: 'felipe', password: 'password', enabled: true).save()
    }
}

	