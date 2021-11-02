# Final Furnishings
A storefront app for the sale and purchase of second-hand furniture. 
MVP includes account creation/login, posting listings for furniture, and purchasing listings. 

https://furnishdb.herokuapp.com/

## Tech Stack
- Platform: Android
- Back-end: Springboot
- Persistence Layer: PostGres SQL

## Mockup
![Screenshot from 2021-10-28 21-05-21](https://user-images.githubusercontent.com/70172259/139373914-fe5acb0f-5b13-4e23-978a-d54999314729.png)

## Initial API Endpoints
- Create Account (POST: [url]/newUser?username={username}&password={password}
- Login (POST: [url]login?username={username}&password={password})
- Logout (POST: [url]logout?username={username})
- Delete Account (DELETE [url]logout?username={username}
- List Furnishings (GET [url]/items)
- List Claimed Furnishings (GET: [url]/items?list={username})
- Display Furnishing (GET [url]/items?search={type, age})
- Add Furnishing (POST: [url]/items?item_name={itemname}&price={price}...)
- Remove Furnishing (DELETE: [url]/items?item_name={itemname})
- Update Furnishing (PATCH: [url]/items?item_name={itemname})

## Contributors
- Niko Holbrook
- Lucas Kotowski
- Julio Lopez
- CJ Turetzky

## Notes
The spring/ directory contains the API functionality of the application.
Specifically, the Spring Boot server and such.
A different directory will contain the Android application that consumes the API.
