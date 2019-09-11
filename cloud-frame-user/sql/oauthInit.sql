CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) NULL DEFAULT NULL,
  `client_id` varchar(256) NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
);

CREATE TABLE `oauth_approvals`  (
  `userId` varchar(256) NULL DEFAULT NULL,
  `clientId` varchar(256) NULL DEFAULT NULL,
  `scope` varchar(256) NULL DEFAULT NULL,
  `status` varchar(10) NULL DEFAULT NULL,
  `expiresAt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `lastModifiedAt` timestamp(0) NOT NULL
);

CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) NULL DEFAULT NULL,
  `client_secret` varchar(256) NULL DEFAULT NULL,
  `scope` varchar(256) NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) NULL DEFAULT NULL,
  `authorities` varchar(256) NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` blob NULL,
  `autoapprove` varchar(256) NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`)
);

CREATE TABLE `oauth_client_token`  (
  `token_id` varchar(256) NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) NULL DEFAULT NULL,
  `client_id` varchar(256) NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
);

CREATE TABLE `oauth_code`  (
  `code` varchar(256) NULL DEFAULT NULL,
  `authentication` blob NULL
);

CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
);