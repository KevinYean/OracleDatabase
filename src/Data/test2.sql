	if(!numbCheckinField.getText().trim().isEmpty()) {
		businessCheckBeg = "SELECT * " 
					+ "FROM  "
					+ "SELECT (sum(checkCount) as totalCheck, businessID FROM( ";
		String operator = numbCheckinBox.getValue().toString();
		String numbCheck = numbCheckinField.getText();
		
		 businessCheckEnd = ") Group by businessID ) WHERE totalCheck " + operator + " " + numbCheck;
		
		
		checkSelected++;