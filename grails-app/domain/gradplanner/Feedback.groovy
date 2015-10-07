package gradplanner

class Feedback {

	static hasOne = [className: ClassName]
	//static belongsTo = [user: User]
	String comment
	Date date

    static constraints = {
    }
}
