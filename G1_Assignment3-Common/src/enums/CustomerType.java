package enums;

/**
 * @author Elroy, Vlad, Lior
 */
public enum CustomerType {
	Person {
		public String toString() {
			return "Person";
		}
	},
	Company {
		public String toString() {
			return "Company";
		}
	};
}
