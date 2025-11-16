INSERT INTO roles (id, authority) VALUES (1, 'ROLE_USER') ON CONFLICT (id) DO NOTHING;
INSERT INTO roles (id, authority) VALUES (2, 'ROLE_FAMILIAR') ON CONFLICT (id) DO NOTHING;
INSERT INTO roles (id, authority) VALUES (3, 'ROLE_INSTITUCION') ON CONFLICT (id) DO NOTHING;

INSERT INTO users(id, username, email, password, huella_total_kg_co2e, huella_transporte_kg, huella_energia_kg, huella_alimentacion_kg, huella_residuos_kg) VALUES (1,'AdrianLS', 'adrianls@eco.com', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', null, null, null, null, null) ON CONFLICT (id) DO NOTHING;

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1) ON CONFLICT (user_id, role_id) DO NOTHING;

INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'auto', 'km', 0.20, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'moto', 'km', 0.12, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'avion', 'km', 0.25, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'bicicleta', 'km', 0.0, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'a pie', 'km', 0.0, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'transporte publico', 'km', 0.08, 'demo', true); -- (Cubre "bus", "tren", "metropolitano")

-- 2. ENERGÍA (Para ambas calculadoras)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'electricidad', 'kWh', 0.45, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'calefaccion', 'horas', 1.5, 'demo', true); -- (Para Registro Diario)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'glp', 'kg', 2.90, 'demo', true); -- (¡ESTE ES EL QUE TE FALTABA!)

-- 3. ENERGÍA - DISPOSITIVOS (Para Registro Diario)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'less2', 'dia', 0.5, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', '2to4', 'dia', 1.0, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'more4', 'dia', 2.0, 'demo', true);

-- 4. ALIMENTACIÓN (Para Calculadora Personal)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('alimentacion', 'omnivora', 'dia', 2.5, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('alimentacion', 'vegetariana', 'dia', 1.5, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('alimentacion', 'vegana', 'dia', 1.0, 'demo', true);

-- 5. RESIDUOS (Para ambas calculadoras)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('residuos', 'general', 'kg', 0.02, 'demo', true);

-- (Añade esto al final de tu import.sql)
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('VIDEO', 'Como Reducir tu Huella de Carbono y Cuidar el PLaneta', 'https://www.youtube.com/watch?v=8A4pYULzp9U', 'Wabi-Sabi',  'hogar', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('ARTICULO', 'Reciclaje 101: lo que sí y lo que no', 'https://www.rts.com/es/blog/recycling-101-how-to-recycle-better/', 'Recicyle Track Systems', 'residuos', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('PODCAST', 'Movilidad Sostenible', 'https://open.spotify.com/show/7a4R0MxdrUW4z3jj9VqzyF', 'Ciclogreen',  'transporte', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('VIDEO', 'Ahorro de Energía en el hogar', 'https://www.youtube.com/watch?v=LyK3F7PLzAg', 'Ministerio de Energia y Minas del Perú',  'energia', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('ARTICULO', 'Compostaje en casa paso a paso', 'https://www.bbva.com/es/sostenibilidad/como-hacer-compost-con-los-residuos-organicos-de-la-casa/', 'BBVA',  'residuos', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('ARTICULO', 'La guía definitiva para no perderte en el mundo de la moda sostenible', 'https://www.vogue.es/moda/articulos/moda-sostenible-que-significa-guia-consejos-marcas', 'Vogue', 'consumo', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('VIDEO', 'El cambio climático y la alimentación saludable', 'https://www.youtube.com/watch?v=jq6u8RoqGOE', 'Canal12Ch', 'alimentacion', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('PODCAST', 'Cuidado del Medio Ambiente', 'https://open.spotify.com/show/1F47qzb5Vb5z2NHMa1yh6i', 'Leslie Serna', 'Medio Ambiente', now());

SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('actividades_diarias_actividad_id_seq', (SELECT MAX(actividad_id) FROM actividades_diarias));