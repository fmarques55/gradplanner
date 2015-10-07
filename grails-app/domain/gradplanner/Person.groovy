package gradplanner

class Person {

	String email
	String name
	String lastName
	String cpf

	static belongsTo = [student: Student, teacher: Teacher]
	//static hasOne = [user: User]

    static constraints = {
    }
}
