
import java.util.Optional;

public abstract class Organization {

	private Position root;
	private int ID = 1;

	public Organization() {
		root = createOrganization();
	}

	protected abstract Position createOrganization();

	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	//*******************New Code*********************//
	/*Summary: 
	 * Only Organization.java received new code
	 * Created a SearchForPositin() to check if the position exists and is open
	 * Created and ID for each employee
	 * Created a new Employee in the Hire() 
	 * Set the the new Employee to the open position
	 */
	
	
	/*Search function to check if the position exists and if it is filled.
	 * Takes in title to find and current root
	 * currentPos is updated at we keep recursively calling the function
	 */
	public Position SearchForPosition(String title, Position currentPos) {

		Position posHolder;
		// Is this the position we are looking for
		if (currentPos.getTitle() == title) {
			return currentPos;
		}
		//Perform SearchForPosition for each child of currentPos
		for (Position p : currentPos.getDirectReports()) {
			posHolder = SearchForPosition(title, p);
			//If posHolder is not null and is not filled, then we know we found and can fill the position
			if ((posHolder != null) && (posHolder.isFilled() != true))
				return posHolder;
		}

		return null;
	}

	public Position hire(Name person, String title) {
		
		//Find the Position
		Position positionToFill = SearchForPosition(title, root);
		
		//Check if position was found
		if (positionToFill == null) {
			System.out.println("Unable to add " + person.getFirst() + " " + person.getLast() + " to " + title);
			return null;
		}
		//Create new Employee 
		Employee newEmployee = new Employee(ID, person);
		
		//Set newEmployee to the position
		positionToFill.setEmployee(Optional.of(newEmployee));
		ID++;
		return positionToFill;

	}
	//*******************End of New Code*********************//
	@Override
	public String toString() {
		return printOrganization(root, "");
	}

	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for (Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
