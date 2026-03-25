# ShopSmart
Mobile Application 1 Final Project(Kotlin)
# BACKEND CHANGES 03-19-2026
 - Added AuthRepository class file to handle login and registration
   - Handles errors on db 
   - handle password hashing via firebase auth
 - Added Ids to register layout buttons
 - Added dependencies
    - App
        - google services
        - firebase bom
        - firestore
        - firebase-auth
    - Project
        - google services
- Added caching to gradle properties to speed up build
- Integrated AuthRepository in LoginActivity
- Integrated AuthRepository in RegisterActivity

## TODO:
 - [ ] Products page
 - [ ] Logout functionality
