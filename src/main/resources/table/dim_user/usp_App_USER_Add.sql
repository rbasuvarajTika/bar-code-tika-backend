USE [TIKA_DEVICE_AUS]
GO
/****** Object:  StoredProcedure [dbo].[usp_App_USER_Add]    Script Date: 08-07-2024 15:15:02 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- ==========================================================
-- Author		: Sridhar Cheepu
-- Create Date	: 2023-09-01
-- Description	: adding a new User
-- ==========================================================

ALTER PROCEDURE [dbo].[usp_App_USER_Add]

	@USER           varchar(155),
	@FIRST_NAME		varchar(50),
	@MIDDLE_NAME    varchar(25),
	@LAST_NAME		varchar(50),
	@ADDRESS1		varchar(150),
	@ADDRESS2		varchar(50),
	@CITY			varchar(50),
	@STATE			varchar(25),
	@ZIP			varchar(10),
	@ROLE	varchar(25)	,
	@USER_NAME	varchar(155)	,
	@USER_EMAIL	varchar(100)	,
	@USER_PHONE	varchar(15)	,
	@USER_MOBILE	varchar(15)	,
	@PREFERRED_NAME	varchar(50)	,
	@SALES_FORCE	varchar(50)	,
	@PASSWORD	varchar(25)	,
	@CONFIRM_PASSWORD	varchar(25)	,
	@PASSWORD_UPDATED_DATE	datetime	,
	@USER_TYPE	varchar(25)	,
	@OTHER_PASSWORD	varchar(25),
	@USER_IMAGE_URL varchar(255)

AS 
BEGIN TRY


	IF @USER_NAME is null 
	 BEGIN
	 IF exists (SELECT * FROM [dbo].[DIM_USER] WHERE [USER_NAME] = @USER_NAME)
		BEGIN
			RAISERROR('##User already exists. Please check the details or Contact Administrator. ##',16,1)
			RETURN
		END
		ELSE BEGIN 
			INSERT INTO [dbo].[DIM_USER] 
			([USER_NAME]  ,[FIRST_NAME],[MIDDLE_NAME],[LAST_NAME]  ,[USER_ROLE],[USER_EMAIL]
	  ,[USER_PHONE] ,[USER_MOBILE],[ADDRESS1] ,[ADDRESS2],[CITY],[STATE],[ZIP]
      ,[PREFERRED_NAME]  ,[SALES_FORCE],[START_DATE],[USER_IMAGE_URL] ,[PASSWORD] ,[CONFIRM_PASSWORD] ,[PASSWORD_UPDATED_DATE]
      ,[USER_STATUS_FLAG]  ,[USER_TYPE] ,[OTHER_PASSWORD],[CREATED_USER] ,[CREATED_DATE])
			VALUES (@USER_NAME  ,@FIRST_NAME,@MIDDLE_NAME,@LAST_NAME ,@ROLE,@USER_EMAIL
	  ,@USER_PHONE ,@USER_MOBILE,@ADDRESS1 ,@ADDRESS2,@CITY,@STATE,@ZIP
      ,@PREFERRED_NAME ,@SALES_FORCE,getDate() ,@USER_IMAGE_URL ,@PASSWORD ,@CONFIRM_PASSWORD ,@PASSWORD_UPDATED_DATE
      ,'Active'  ,@USER_TYPE ,@OTHER_PASSWORD,@USER, getdate())
		END
		
				 
		END




END TRY
BEGIN CATCH
    THROW;
END CATCH;
