{% include navigation.html %}

# Success Ideas 
* [Linkedin](https://www.linkedin.com/) 
    - Platform for students: PUSD linkedin, sort by classes, 
    - Rogerhub, grade generator  
    - More DN specific = can contact principal 
    - Sponsored by DNHS 

# Project Description 
* LinkDN
    - This is a networking platform where students of Del Norte High School can make profiles that allow students to connect and find common interests through similar career pathways. 
    - Services:
         - Profile creation with customizable features(bio, profile picture, classes taken, etc.)
         - Grade calculator
         - Search for other students in the school
         - See other club activities for school wide events 
         - Connect with other students for various academic endeavours 
         - Chat with students
         - Admin can make school-wide posts
         - Class schedule connecter 


# Wiring

<img src="src/main/resources/static/images/web-linkdn.png"
     alt="Markdown Monster icon"
     style="float: left; margin-right: 10px;" />

## Templates:
 * index.html
 * layout.html
    - implement elements of SCSS
 * fragments(directory)
    - nav
    - footer
    - body


## Design Plan: 
* Color Scheme: 
      * Primary navy blue: #000080
![](https://github.com/ridhimainukurti/p1-Valid/blob/master/src/main/resources/static/images/blue.png)
 * Secondary: #006400
![](https://github.com/ridhimainukurti/p1-Valid/blob/master/src/main/resources/static/images/green.png)
      * used as accents across website
* font: Poppins, sans-serif
* Use Bootstrap for nav bar and templating for most efficient use of sassy

### Covering SASS
 * Resources:
   * [Bootstrap](https://getbootstrap.com/docs/5.0/getting-started/introduction/#starter-template)
   * [Customizing SASS](https://getbootstrap.com/docs/5.0/customize/sass/)


## Technical Ideas/Plans
- Profile creation with customizable features(bio, profile picture, classes taken, etc.)
    - sqlite.db
    - Use MVC for profile signup/login; similar to old project [here]()
    - db setup similar to [this]()
- Grade calculator
- Search for other students in the school
- See other club activities for school wide events 
- Connect with other students for various academic endeavours 
- Chat with students
- Admin can make school-wide posts
- Class schedule connecter 



