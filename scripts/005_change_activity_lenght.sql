UPDATE pg_attribute SET atttypmod = 500+4
WHERE attrelid = 'activity'::regclass
      AND attname = 'name';

UPDATE pg_attribute SET atttypmod = 500+4
WHERE attrelid = 'activity'::regclass
      AND attname = 'short_description';

UPDATE pg_attribute SET atttypmod = 5000+4
WHERE attrelid = 'activity'::regclass
      AND attname = 'description';

