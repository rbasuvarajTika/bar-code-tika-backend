USE [TIKA_DEVICE_AUS]
GO
/****** Object:  StoredProcedure [dbo].[usp_App_USER_Mod]    Script Date: 08-07-2024 15:20:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- ==========================================================
-- Author		: Sridhar Cheepu
-- Create Date	: 2023-09-01
-- Description	: adding a new User
-- ==========================================================

ALTER PROCEDURE [dbo].[usp_App_USER_Mod]

	@User           varchar(155),
	@FIRST_NAME		varchar(50),
	@MIDDLE_NAME    varchar(25),
	@LAST_NAME		varchar(50),
	@ADDRESS1		varchar(150),
	@ADDRESS2		varchar(50),
	@CITY			varchar(50),
	@STATE			varchar(25),
	@ZIP			varchar(10),
	@TITLE	varchar(100)	,
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
	@USER_STATUS_FLAG	varchar(25)	,
	@USER_TYPE	varchar(25)	,
	@OTHER_PASSWORD	varchar(25),
	@USER_IMAGE_URL varchar(255)

AS 
BEGIN 

	 IF exists (SELECT * FROM [dbo].[DIM_USER] WHERE [USER_NAME] = @USER_NAME)
		BEGIN
		UPDATE [dbo].[DIM_USER] 
		SET [FIRST_NAME]=@FIRST_NAME,
			[MIDDLE_NAME]=@MIDDLE_NAME,
			[LAST_NAME]=@LAST_NAME ,
			[TITLE]=@TITLE  ,
			[USER_ROLE]=@ROLE,
			[USER_EMAIL]=@USER_EMAIL,
			[USER_PHONE]= @USER_PHONE,
			[USER_MOBILE]=@USER_MOBILE,
			[ADDRESS1] =@ADDRESS1,
			[ADDRESS2]=@ADDRESS2,
			[CITY]=@CITY,
			[STATE]=@STATE,
			[ZIP]=@ZIP,
			[PREFERRED_NAME]=@PREFERRED_NAME  ,
			[SALES_FORCE]=@SALES_FORCE, 
			[USER_IMAGE_URL]=@USER_IMAGE_URL,
			[PASSWORD]=@PASSWORD, 
			[CONFIRM_PASSWORD]=@CONFIRM_PASSWORD ,
			[PASSWORD_UPDATED_DATE]=@PASSWORD_UPDATED_DATE,
			[USER_STATUS_FLAG]=@USER_STATUS_FLAG  ,
			[USER_TYPE]=@USER_TYPE ,
			[OTHER_PASSWORD]=@OTHER_PASSWORD,
			[UPDATED_USER]=@USER ,
			[CREATED_DATE]=getDate()
		where [USER_NAME]=@USER_NAME
		

		
	END	
		

		END