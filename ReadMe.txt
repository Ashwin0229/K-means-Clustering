=========================================================================================
Part 1 :
=========================================================================================

compile : javac KMeans.java
execute : 
	k = 2
		java KMeans Koala.jpg 2 koala_out.jpg
		java KMeans Penguins.jpg 2 Penguins_out.jpg
	k = 5
		java KMeans Koala.jpg 5 koala_out.jpg
		java KMeans Penguins.jpg 5 Penguins_out.jpg
	k = 10
		java KMeans Koala.jpg 10 koala_out.jpg
		java KMeans Penguins.jpg 10 Penguins_out.jpg
	k = 15
		java KMeans Koala.jpg 15 koala_out.jpg
		java KMeans Penguins.jpg 15 Penguins_out.jpg
	k = 20
		java KMeans Koala.jpg 20 koala_out.jpg
		java KMeans Penguins.jpg 20 Penguins_out.jpg

=========================================================================================
Part 2:
=========================================================================================

execute:
(Please uncomment the desired algorithm in the main function of algorithm4.py file and run the below command)

python algorithm4.py