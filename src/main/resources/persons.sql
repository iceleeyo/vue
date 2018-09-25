/*
Navicat Oracle Data Transfer
Oracle Client Version : 12.2.0.1.0

Source Server         : 10.100.1.215
Source Server Version : 110200
Source Host           : 10.100.1.215:1521
Source Schema         : SC_PORTAL_TEST

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2018-09-25 11:14:21
*/


-- ----------------------------
-- Table structure for PERSONS
-- ----------------------------
DROP TABLE "SC_PORTAL_TEST"."PERSONS";
CREATE TABLE "SC_PORTAL_TEST"."PERSONS" (
"ID" NUMBER(11) NOT NULL ,
"CREATE_DATETIME" TIMESTAMP(6)  NULL ,
"EMAIL" VARCHAR2(50 BYTE) NULL ,
"PHONE" VARCHAR2(60 BYTE) NULL ,
"SEX" VARCHAR2(70 BYTE) NULL ,
"USERNAME" VARCHAR2(80 BYTE) NULL ,
"ZONE" VARCHAR2(90 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Indexes structure for table PERSONS
-- ----------------------------

-- ----------------------------
-- Checks structure for table PERSONS
-- ----------------------------
ALTER TABLE "SC_PORTAL_TEST"."PERSONS" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table PERSONS
-- ----------------------------
ALTER TABLE "SC_PORTAL_TEST"."PERSONS" ADD PRIMARY KEY ("ID");
