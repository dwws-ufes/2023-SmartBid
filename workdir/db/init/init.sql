INSERT INTO Pessoa (id, uuid, version, email, identificacao, nome, telefone, tipoIdentificacao, senha, role)
VALUES (LAST_INSERT_ID(), UUID(), 0, 'admin@admin', '99999999999', 'admin', '9999999999', 0, 'PBKDF2WithHmacSHA256:2048:e1Dyx83B1AanfJ3JhWtV+kVIqsjADDqy/pHMvntEWBc=:sVH99XQF8DlVk9McbFPNUzLYPp4ySnKdq1EPbLNBKnM=', 'ADMIN');
