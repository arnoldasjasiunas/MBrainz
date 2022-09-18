+-----------------------------------------------------------------------------------------------------------
|                                               Coding task                                                     
+-----------------------------------------------------------------------------------------------------------
|                                                                                                           
| * We want you to create a single activity Android application    
| * Input would be a search term for places used for performing or producing music  
| * Output would be places as pins on a map                          
|                                                                                                           
+-----------------------------------------------------------------------------------------------------------

+-----------------------------------------------------------------------------------------------------------
|                                               Requirements                                                     
+-----------------------------------------------------------------------------------------------------------
|                                                                                                           
| * Must be written in Kotlin   
| * Use MusicBrainz API https://wiki.musicbrainz.org/Development
| * Places returned per request should be limited, but all places must be displayed on map. For example 
|   there 100 places for search term, but limit is 20, so you need 5 request to get all the places 
| * Make this limit easy to tune in code    
| * Display places opened 1990 or later 
| * Every pin has a lifespan, meaning after it expires, pin should be removed from the map. 
|   Lifespan calculation: open_year - 1990 = lifespan_in_seconds. Example: 2017 - 1990 = 27s                            
|                                                                                                           
+-----------------------------------------------------------------------------------------------------------

+-----------------------------------------------------------------------------------------------------------
|                                                Karma for                                                     
+-----------------------------------------------------------------------------------------------------------
|                                                                                                           
| * Clean code and best OOP practices
| * Kotlin specific features 
| * Test-covered code                       
|                                                                                                           
+-----------------------------------------------------------------------------------------------------------