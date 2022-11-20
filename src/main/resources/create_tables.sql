/*
Created: 11.09.2022
Modified: 11.09.2022
Model: PostgreSQL 12
Database: PostgreSQL 12
*/

-- Create tables section -------------------------------------------------

-- Table Product

CREATE TABLE "Product"
(
    "ProductId" UUID NOT NULL,
    "Name"      Bigint NOT NULL
)
    WITH (autovacuum_enabled = true)
;

ALTER TABLE "Product"
    ADD CONSTRAINT "PK_Product" PRIMARY KEY ("ProductId")
;

-- Table PriceList

CREATE TABLE "PriceList"
(
    "PriceListId"    UUID   NOT NULL,
    "ProductId"      Bigint NOT NULL,
    "ModelId"        UUID,
    "Price"          Bigint NOT NULL,
    "ManufacturerId" UUID
)
    WITH (autovacuum_enabled = true)
;

CREATE INDEX "IX_Relationship2" ON "PriceList" ("ModelId")
;

CREATE INDEX "IX_Relationship3" ON "PriceList" ("ManufacturerId")
;

CREATE INDEX "IX_Relationship5" ON "PriceList" ("ProductId")
;

ALTER TABLE "PriceList"
    ADD CONSTRAINT "PK_PriceList" PRIMARY KEY ("PriceListId")
;

-- Table Manufacturer

CREATE TABLE "Manufacturer"
(
    "ManufacturerId" UUID NOT NULL,
    "Name"           Text NOT NULL,
    "Country"        Text NOT NULL,
    "Site"           Text
)
    WITH (autovacuum_enabled = true)
;

ALTER TABLE "Manufacturer"
    ADD CONSTRAINT "PK_Manufacturer" PRIMARY KEY ("ManufacturerId")
;

-- Table Model

CREATE TABLE "Model"
(
    "ModelId"        UUID NOT NULL,
    "Name"           Text NOT NULL,
    "ManufacturerId" UUID NOT NULL
)
    WITH (autovacuum_enabled = true)
;

CREATE INDEX "IX_Relationship1" ON "Model" ("ManufacturerId")
;

ALTER TABLE "Model"
    ADD CONSTRAINT "PK_Model" PRIMARY KEY ("ModelId")
;

-- Table Client

CREATE TABLE "Client"
(
    "ClientId"  UUID NOT NULL,
    "Name"      Text NOT NULL,
    "Telephone" Bigint
)
    WITH (autovacuum_enabled = true)
;

ALTER TABLE "Client"
    ADD CONSTRAINT "PK_Client" PRIMARY KEY ("ClientId")
;

-- Table Order

CREATE TABLE "Order"
(
    "OrderId"     UUID NOT NULL,
    "Date"        Date NOT NULL,
    "PriceListId" UUID NOT NULL,
    "Count"       Bigint,
    "ClientId"    UUID NOT NULL
)
    WITH (autovacuum_enabled = true)
;

CREATE INDEX "IX_Relationship6" ON "Order" ("PriceListId")
;

CREATE INDEX "IX_Relationship7" ON "Order" ("ClientId")
;

ALTER TABLE "Order"
    ADD CONSTRAINT "PK_Order" PRIMARY KEY ("OrderId")
;

-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE "Model"
    ADD CONSTRAINT "Manufacturer_To_Model_FK"
        FOREIGN KEY ("ManufacturerId")
            REFERENCES "Manufacturer" ("ManufacturerId")
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE "PriceList"
    ADD CONSTRAINT "Model_To_PriceList_FK"
        FOREIGN KEY ("ModelId")
            REFERENCES "Model" ("ModelId")
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE "PriceList"
    ADD CONSTRAINT "Manufacturer_To_PriceList_FK"
        FOREIGN KEY ("ManufacturerId")
            REFERENCES "Manufacturer" ("ManufacturerId")
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE "PriceList"
    ADD CONSTRAINT "Product_To_PriceList_FK"
        FOREIGN KEY ("ProductId")
            REFERENCES "Product" ("ProductId")
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE "Order"
    ADD CONSTRAINT "PriceList_To_Order_FK"
        FOREIGN KEY ("PriceListId")
            REFERENCES "PriceList" ("PriceListId")
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE "Order"
    ADD CONSTRAINT "Client_To_Order_FK"
        FOREIGN KEY ("ClientId")
            REFERENCES "Client" ("ClientId")
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

