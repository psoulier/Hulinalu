#Overview
There are many web-based tools and applications for navigating, locating places,
finding reviews, weather conditions, and so forth. Few services, however,
provide current, real-time information relevant to specific physical
locations. This is not overly surprising considering the difficulty in
collecting and interpreting this type of data. Many locations are remote or
exist where electronic equipment is not viable due to cost, environment etc.
Even when web cams are available, they offer only a limited overall view of the
surrounding environment. Navigation tools can provide in-route traffic
conditions, but do not give information about the destination. 

Consider, for example, an individual planning a trip to the beach. Various
factors are important depending on what activities are planned. Some information
is readily available. Surf and weather conditions are easily obtained and some
beaches may even have web cams. However, obtaining additional information such
as crowd size, parking availability, and water clarity for divers/snorkelers is
not as readily available. 

The best source of information may be people currently at a location with an
Internet-connected mobile device. An application that would allow people to
share information about a location who are currently there with others who are
interested in that location would help address this problem. In essence, the
system will dynamically present current conditions for a location by utilizing
the information provided by people at that location.

#Approach
This web application will provide two basic components: the producer and the
consumer. The producer component allows users of the application to contribute
time-relevant data for a specific location. In theory, if a fraction of
the people at a place of interest provide some update-to-date information on
current conditions, an accurate representation of the overall conditions can be produced. 
The consumer aspect allows users to check the current conditions
provided by the producers of a specific location. The concept is that
consumers then can become producers when they decide to go to a location. The hope is that,
for reasonably popular locations, a steady feed of up-to-date
information is produced.

To minimize subjectivity and to allow for statistical analysis, a set of locals
will be provided (e.g.: beach, trail, etc.) with predefined information
categories. Each category will have a predefined set of selections that can
easily be assigned a numerical value for subsequent processing. By using simple,
predefined categories choices, The intent is use simple, predefined categories and
choices to make the task of "producing" information updates as quick and simple as
possible so as not to disuade users from participating.

There are several issues that arise from such a system:

1. Users that only consume data and do not participate in the information
  production.
2. Users that generate false data - specifically when not physically present at a
  location.
3. A location without sufficient updates to provide accurate data. This may exist
  as variable sized gaps in time.


## Producer
The Producer component will allow a user to provide current information for a
location they are physically at. Location services provided by a mobile device
(i.e.: GPS) will allow the web app to determine if a user is at a location
within some given tolerance. When a user runs the application, they will be
provided an update screen with predefined information fields if they are at a known location. 
By utilizing physical location, this prevents users from SPAMing the system or arbitrarily updating
conditions for a location at which they are not present. This addresses issues 2
above.

To facilitate the addition of new locations, a user may suggest the addition of
their current location. When a sufficient number of users add the same
location (as determined by location services of the device), the location will
become available to other users for future use.

## Consumer
The consumer interface will allow users to view various locations and the associated
conditions of that location. In terms of usability, this aspect of the
application would be similar to many other services providing location-based
information such as customer reviews, map location, etc. A list of locations will
be provided based on user search criteria. By selecting a location, additional
information will be provided about that location.

## Credit System
A "credit" system will be used to avoid the first issue from above. Users will
use credits to access update-to-date information for a specific location and will receive
credit by providing information when physically present at a location. The
general intent of this system is to encourage a "give and take" mentality and
would generally not pose a noticeable hindrance to most users. When a user
creates an account, they will be given a sufficient number of credits to utilize
the system without the need to immediately "produce" information.

This system may not work - especially if it becomes overly intrusive to the user
experience. Care must be taken to ensure this system is more of a motivational
incentive rather than a hinderance. Another option may be to use badges or another 
rewards-based system that is not compulsory.

## Data Analysis
Data provided by users at a location will be stored with a time stamp. Basic
statistics will be applied to data within a certain time interval to determine
the common consensus for the various conditions of a location. 

# Deliverable 1
Deliverable 1 will consist of the following components:

1. Landing page
2. Sign Up and Profile
3. Home

## Landing Page
The landing page is simply the first page shown for users that are not logged
in. It will describe the service the website provides, a login for
existing users, and a link for new users to create an account. While simple in
function, this page should be ascetically pleasing and convey the general
concept of the service the website provides.

## Sign Up and Profile
The sign up page will allow a user to create a new account. This will be a
typical account creation form requiring the user to provide a certain amount of
information and optionally allowing the user to add additional information to
customize their profile. Required fields for sign up are the following:
- Valid e-mail address
- Account nickname to identify user online

Optional information will include:
- Full name
- General location (zip code, address, or similar)
- Mobile phone number 


## Home 
The Home page is the primary interface for the user and will contain the
system's core functionality. It will show the users current location and provide
the option to share information about that location if it's known to the system.
If the location is not known, it will provide a mechanism to suggest the
location to be added or to be ignored (if, for example, its their home, office,
etc.). A location that is "suggested" will only become publicly available once a
sufficient number of users suggest the same place.

This page will also provide an interface to view the conditions of various
locations based on a category (e.g.: trails, beaches, etc.). This interface will
allow the user to select predefined categories. Once selected, locations
available in the system within a certain radius will be shown on a map. The user
will be able to select markers on the map to show detailed information about
that location. Selecting a location will deduct credits from the user's account.
The information associated to 

# Deliverable 2
Deliverable 1 provides only a simple mechanism for finding locations of
interest. The primary goal of Deliverable 2 is to add more comprehensive search
features. These features would provide the ability to search for locations based
on query strings, locations (e.g.: addresses, zip codes, etc.), or locations
with specific current conditions (e.g.: available parking, not crowded, etc.). 

# Deliverable 3
The first two deliverables focused primarily on core functionality. The focus of
the third deliverable would allow users to post comments or useful information
about the location. A scoring system would be used to help identify the most
useful comments. Uses would also be able to submit pictures. In a similar manner,
pictures can be voted on to allow the most relevant to be the most visible. The comments
and pictures are not intended to be representative of current conditions. Instead they 
are to provide general information about a location. 

# Future Deliverables
- Key contributers. Input from a user that consistenly provides accurate information will be weighted more heavily. 
- Suggest new information categories for a location.

# Mockup
The following images show the landing page for the website. This just provides basic
login mechanism and method to begin the new user sign up process.
## PC Screenshot
![PC Landing](/mockup/pc-landing.png)
## Mobile Screenshot
![Mobile Landing](/mockup/mobile-landing.png)


The following two images show the basic concept of the search functionality for both PC and mobile 
screen geometries. The user simply selects the type of location they're interested in and a list of
locations is provided. 
## PC Screenshot
![PC Search](/mockup/pc-search.png)
## Mobile Screenshot
![Mobile Search](/mockup/mobile-search.png)

The two images below show the application detecting a location and requesting that 
the user share information about it.
## PC Screenshot
![PC Share](/mockup/pc-share.png)
## Mobile Screenshot
![Mobile Share](/mockup/mobile-share.png)
