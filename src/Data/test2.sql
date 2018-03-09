SELECT subName
FROM SUBCATEGORY 
WHERE mainCategory = 'Drugstores' AND subName in(
		SELECT subName
		FROM SUBCATEGORY 
		WHERE mainCategory = 'Restaurants' AND subName in (
														SELECT S.subName
														FROM SUBCATEGORY S
														WHERE S.mainCategory = 'Food'))