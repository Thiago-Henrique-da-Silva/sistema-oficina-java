CREATE DATABASE SistemaOficinaMecanica;

       USE SistemaOficinaMecanica;

CREATE TABLE `Carros` (
                          `id` int unsigned NOT NULL AUTO_INCREMENT,
                          `marca` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `modelo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `placa` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `placa` (`placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Clientes` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                            `nome` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                            `cpf` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                            `telefone` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `Clientes_UNIQUE` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `OrdemServico` (
                                `id` int unsigned NOT NULL AUTO_INCREMENT,
                                `cliente_id` int unsigned NOT NULL,
                                `carro_id` int unsigned NOT NULL,
                                `status` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                PRIMARY KEY (`id`),
                                KEY `OrdemServico_Clientes_FK` (`cliente_id`),
                                KEY `OrdemServico_Carros_FK` (`carro_id`),
                                CONSTRAINT `OrdemServico_Carros_FK` FOREIGN KEY (`carro_id`) REFERENCES `Carros` (`id`),
                                CONSTRAINT `OrdemServico_Clientes_FK` FOREIGN KEY (`cliente_id`) REFERENCES `Clientes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;