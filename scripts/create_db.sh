DB_NAME="InquireNet"
DB_USER="postgres"
DB_HOST="localhost"
DB_PORT="5432"

# Create the database
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -c "CREATE DATABASE \"$DB_NAME\";"

# Grant necessary privileges
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -c "ALTER ROLE $DB_USER SET default_transaction_isolation TO 'read committed';"
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -c "GRANT ALL PRIVILEGES ON DATABASE \"$DB_NAME\" TO $DB_USER;"

echo "Database '$DB_NAME' created successfully."
