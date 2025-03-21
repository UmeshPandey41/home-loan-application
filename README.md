The error **"role 'admin' does not exist"** means that the `admin` user hasn't been created yet. You need to create it first.  

### **Solution: Create the `admin` User**  

1. Run the following command inside `psql` (since you're already logged in as `postgres`):  
   ```sql  [psql -U postgres] ----- first time while login DB
   CREATE ROLE admin WITH LOGIN PASSWORD 'admin';
   ```
2. Grant the user permission to manage the database:  
   ```sql
   ALTER ROLE admin CREATEDB CREATEROLE SUPERUSER;
   ```
3. If the `loandbs` database doesn't exist, create it:  
   ```sql
   CREATE DATABASE loandbs OWNER admin;
   ```
4. Grant privileges on the database:  
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE loandbs TO admin;
   ```

### **Try Logging in Again**  
Now, exit `psql`:
```sh
\q
```
Then, try logging in as `admin`:
```sh
psql -U admin -d loandbs -h localhost
```
