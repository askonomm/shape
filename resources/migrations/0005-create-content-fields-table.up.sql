CREATE TABLE IF NOT EXISTS content_fields (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  content_id INTEGER NOT NULL,
  field_identifier TEXT NOT NULL,
  field_value TEXT,
  updated_at INTEGER
);
