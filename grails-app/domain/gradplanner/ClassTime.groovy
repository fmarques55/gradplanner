package gradplanner

class ClassTime {

	String dayInWeek
	String startTime
	String endTime
	String breakTime
	static belongsTo = [className: ClassName]

    static constraints = {
    }
}
