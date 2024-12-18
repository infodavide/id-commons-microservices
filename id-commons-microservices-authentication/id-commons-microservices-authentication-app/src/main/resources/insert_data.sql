INSERT INTO configuration_properties (deletable, read_only, name, creation_date, modification_date, data_type, data, scope, data_type_def, label, mini, maxi, default_data) VALUES
  (true, true, 'schema.version', NOW(), NOW(), 'STRING', '0.0.1', NULL, NULL, 'DB schema version', NULL, NULL, '1.0.0');

INSERT INTO users (deletable, name, display_name, creation_date, modification_date, connections_count, email, expiration_date, last_connection_date, last_ip, locked, password, roles) VALUES
  (true, 'admin', 'Administrator', NOW(), NOW(), 0, 'contact@infodavid.org', NULL, NULL, NULL, false, '21232F297A57A5A743894A0E4A801FC3', 'ADMINISTRATOR'),
  (true, 'anonymous', 'Anonymous', NOW(), NOW(), 0, NULL, NULL, NULL, NULL, false, '', 'ANONYMOUS');
