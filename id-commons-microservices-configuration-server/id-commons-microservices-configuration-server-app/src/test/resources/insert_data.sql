INSERT INTO configuration_properties (deletable, read_only, name, creation_date, modification_date, data_type, data, application, scope, data_type_def, label, mini, maxi, default_data) VALUES
  (false, true, 'schema.version', NOW(), NOW(), 'STRING', '1.0.0', NULL, NULL, NULL, 'DB schema version', NULL, NULL, '1.0.0'),
  (false, true, 'key11', NOW(), NOW(), 'STRING', 'value11-0', 'app1', 'default', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key12', NOW(), NOW(), 'STRING', 'value12-0', 'app1', 'default', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key11', NOW(), NOW(), 'STRING', 'value11-1', 'app1', 'dev', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key12', NOW(), NOW(), 'STRING', 'value12-1', 'app1', 'dev', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key11', NOW(), NOW(), 'STRING', 'value11-2', 'app1', 'production', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key12', NOW(), NOW(), 'STRING', 'value12-2', 'app1', 'production', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key21', NOW(), NOW(), 'STRING', 'value21-0', 'app2', 'default', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key22', NOW(), NOW(), 'STRING', 'value22-0', 'app2', 'default', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key21', NOW(), NOW(), 'STRING', 'value21-1', 'app2', 'dev', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key22', NOW(), NOW(), 'STRING', 'value22-1', 'app2', 'dev', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key21', NOW(), NOW(), 'STRING', 'value21-2', 'app2', 'production', NULL, 'latest', NULL, NULL, NULL),
  (false, true, 'key22', NOW(), NOW(), 'STRING', 'value22-2', 'app2', 'production', NULL, 'latest', NULL, NULL, NULL);