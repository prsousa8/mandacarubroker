CREATE TABLE IF NOT EXISTS stock(
                      id VARCHAR PRIMARY KEY,
                      symbol VARCHAR NOT NULL,
                      company_name VARCHAR NOT NULL,
                      price FLOAT NOT NULL
);

