CREATE TABLE IF NOT EXISTS content_fields (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  content_id INTEGER NOT NULL,
  identifier TEXT NOT NULL,
  value TEXT,
  updated_at INTEGER
);
