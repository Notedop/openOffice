DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS USER_ACCOUNT;
DROP TABLE IF EXISTS ROLES_FEATURES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS ROLES;
DROP TABLE IF EXISTS FEATURES;
DROP TABLE IF EXISTS ACCOUNTS;
DROP TABLE IF EXISTS REPORTS;

CREATE TABLE USERS
(
    ID                    INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME            VARCHAR(100) NOT NULL,
    LAST_NAME             VARCHAR(100) NOT NULL,
    USER_NAME             VARCHAR(100) NOT NULL,
    EMAIL                 VARCHAR(100) NOT NULL,
    PASSWORD              VARCHAR(150) NOT NULL,
    ENCRYPTION_TYPE       VARCHAR(50)  NOT NULL,
    FAILED_LOGIN_ATTEMPTS INT      DEFAULT 0,
    LAST_SUCCESS_LOGIN    DATETIME DEFAULT NULL,
    ENABLED               INT      DEFAULT 1
);

CREATE TABLE ROLES
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL
);

CREATE TABLE FEATURES
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL
);

CREATE TABLE ACCOUNTS
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL
);

CREATE TABLE USER_ROLES
(
    ID       INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID  INT NOT NULL,
    ROLES_ID INT NOT NULL,
    CONSTRAINT FK_USER_ROLES_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES USERS (ID),
    CONSTRAINT FK_USER_ROLES_ROLES_ID FOREIGN KEY (ROLES_ID)
        REFERENCES ROLES (ID)
);

CREATE TABLE ROLES_FEATURES
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    ROLES_ID    INT NOT NULL,
    FEATURES_ID INT NOT NULL,
    CONSTRAINT FK_ROLES_FEATURES_ROLES_ID FOREIGN KEY (ROLES_ID)
        REFERENCES ROLES (ID),
    CONSTRAINT FK_ROLES_FEATURES_FEATURES_ID FOREIGN KEY (FEATURES_ID)
        REFERENCES FEATURES (ID)
);

CREATE TABLE USER_ACCOUNT
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID  INT NOT NULL,
    ACCOUNT_ID INT NOT NULL,
    CONSTRAINT FK_USER_ACCOUNT_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES USERS (ID),
    CONSTRAINT FK_USER_ACCOUNT_ACCOUNT_ID FOREIGN KEY (ACCOUNT_ID)
        REFERENCES ACCOUNTS (ID)
);


CREATE TABLE ADAPTER_CONFIG
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    DATA CLOB         NOT NULL,
    HREF VARCHAR(100) NOT NULL
);

CREATE TABLE REPORTS
(
    ID      INT AUTO_INCREMENT PRIMARY KEY,
    NAME    VARCHAR(50)  NOT NULL,
    ACCOUNT_ID VARCHAR(100) NOT NULL,
    CONSTRAINT FK_REPORTS_ACCOUNT_ID FOREIGN KEY (ACCOUNT_ID)
        REFERENCES ACCOUNTS (ID)
);



INSERT INTO ROLES (NAME)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO FEATURES(NAME)
VALUES ('SEARCH'),
       ('EDIT_USERS'),
       ('REMOVE_USERS'),
       ('ADMIN_CONSOL');

INSERT INTO USERS (FIRST_NAME, LAST_NAME, USER_NAME, EMAIL, PASSWORD, ENCRYPTION_TYPE)
VALUES ('Raoul', 'van Hal', 'admin', 'raoul2405@hotmail.com', '$2a$12$zSVI0EalcRoWkWkjaURmfe2F1UTKdMLbSGyYEYaoWFa7ZM7dTuSFS', 'none');

INSERT INTO USERS (FIRST_NAME, LAST_NAME, USER_NAME, EMAIL, PASSWORD, ENCRYPTION_TYPE)
VALUES ('Raoul2', 'van Hal2', 'raoul', 'raoul2405@hotmail.com', '$2a$12$EE5gft2jLmPXTpco.VCaJ.1IhSdl4xV7sMr6vOo7RuH8iPW0RUkq.', 'none');

INSERT INTO USER_ROLES (USER_ID, ROLES_ID)
VALUES (SELECT ID FROM USERS WHERE USER_NAME = 'admin', SELECT ID FROM ROLES WHERE NAME = 'ROLE_ADMIN');

INSERT INTO USER_ROLES (USER_ID, ROLES_ID)
VALUES (SELECT ID FROM USERS WHERE USER_NAME = 'raoul', SELECT ID FROM ROLES WHERE NAME = 'ROLE_USER');

INSERT INTO ACCOUNTS (NAME) VALUES ('MARVEL');
INSERT INTO ACCOUNTS (NAME) VALUES ('IMAGE');
INSERT INTO ACCOUNTS (NAME) VALUES ('DC');

INSERT INTO USER_ACCOUNT (USER_ID, ACCOUNT_ID) VALUES (SELECT ID FROM USERS WHERE USER_NAME = 'admin', SELECT ID FROM ACCOUNTS WHERE NAME = 'MARVEL');
INSERT INTO USER_ACCOUNT (USER_ID, ACCOUNT_ID) VALUES (SELECT ID FROM USERS WHERE USER_NAME = 'admin', SELECT ID FROM ACCOUNTS WHERE NAME = 'IMAGE');
INSERT INTO USER_ACCOUNT (USER_ID, ACCOUNT_ID) VALUES (SELECT ID FROM USERS WHERE USER_NAME = 'raoul', SELECT ID FROM ACCOUNTS WHERE NAME = 'DC');

INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'marvel1', SELECT ID FROM ACCOUNTS WHERE NAME = 'MARVEL');
INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'marvel2', SELECT ID FROM ACCOUNTS WHERE NAME = 'MARVEL');
INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'marvel3', SELECT ID FROM ACCOUNTS WHERE NAME = 'MARVEL');

INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'IMAGE1', SELECT ID FROM ACCOUNTS WHERE NAME = 'IMAGE');
INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'IMAGE2', SELECT ID FROM ACCOUNTS WHERE NAME = 'IMAGE');
INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'IMAGE3', SELECT ID FROM ACCOUNTS WHERE NAME = 'IMAGE');

INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'DC1', SELECT ID FROM ACCOUNTS WHERE NAME = 'DC');
INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'DC2', SELECT ID FROM ACCOUNTS WHERE NAME = 'DC');
INSERT INTO REPORTS (NAME, ACCOUNT_ID) VALUES ( 'DC3', SELECT ID FROM ACCOUNTS WHERE NAME = 'DC');

INSERT INTO ADAPTER_CONFIG (DATA, HREF)
VALUES ('<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <xsl:element name="result">
            <xsl:for-each select="/response/results">
                <xsl:call-template name="volume"/>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>


    <xsl:template name="volume">
        <xsl:element name="volume">
            <xsl:element name="name">volume</xsl:element>
            <xsl:apply-templates select="aliases"/>
            <xsl:apply-templates select="api_detail_url"/>
            <xsl:apply-templates select="first_issue/id"/>
            <xsl:apply-templates select="name"/>
            <xsl:apply-templates select="publisher/id"/>
            <xsl:apply-templates select="start_year"/>
            <xsl:apply-templates select="date_last_updated"/>
            <xsl:apply-templates select="description"/>
            <xsl:apply-templates select="count_of_issues"/>
            <xsl:element name="images">
                <xsl:for-each select="image/*">
                    <xsl:apply-templates select="."/>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="issues">
                <xsl:for-each select="issues/issue">
                    <xsl:element name="issue">
                        <xsl:element name="name">issue</xsl:element>
                        <xsl:apply-templates select="."/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>

        </xsl:element>
    </xsl:template>

    <xsl:template match="aliases">
        <xsl:element name="aliases">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="api_detail_url">
        <xsl:element name="detailedUrl">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="first_issue/id">
        <xsl:element name="firstIssueId">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>


    <xsl:template match="name">
        <xsl:element name="volumeName">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="publisher/id">
        <xsl:element name="publisherId">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="start_year">
        <xsl:element name="startYear">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="/response/results/image/*">
        <xsl:if test="name() != ''image_tags''">
            <xsl:element name="image">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:if>
    </xsl:template>

    <xsl:template match="/response/results/issues/issue/*">

        <xsl:if test="name() = ''api_detail_url''">
            <xsl:element name="issueUrl">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="name() = ''id''">
            <xsl:element name="issueId">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="name() = ''name''">
            <xsl:element name="issueName">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="name() = ''issue_number''">
            <xsl:element name="issueNumber">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:if>
    </xsl:template>

    <xsl:template match="date_last_updated">
        <xsl:element name="lastupdated">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="description">
        <xsl:element name="description">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="count_of_issues">
        <xsl:element name="issuecount">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>


</xsl:stylesheet>', 'TESTDATA')
