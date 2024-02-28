-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 28, 2024 at 07:31 AM
-- Server version: 8.0.32
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`) VALUES
(1, 'asian'),
(2, 'Italian'),
(3, 'mexican'),
(4, 'american'),
(5, 'french'),
(6, 'vegetarian');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_description` varchar(255) DEFAULT NULL,
  `product_image` varchar(255) DEFAULT NULL,
  `product_price` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FK1cf90etcu98x1e6n9aks3tel3` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_description`, `product_image`, `product_price`, `product_name`, `category_id`) VALUES
(1, 'Kothu is a spicy and flavorful South Asian street food features chopped roti, spiced curry flavors,vegetables and meat', 'asian2.jpg', '1300.00', 'Chicken Kothu', 1),
(2, 'Biryani is a fragrant rice dish with spiced meat, saffron, and aromatic flavors, popular in South Asian cuisine.', 'asian1.jpg', '2100.00', 'Chicken Briyani', 1),
(258, 'Kimbap is a Korean dish – seaweed-wrapped rice rolls with vegetables and meat', 'asian3.jpg', '1700.00', 'Kimbap', 1),
(259, 'Kimbap is a Korean dish – seaweed-wrapped rice rolls with vegetables', 'asian4.jpg', '1750.00', 'Ramen', 1),
(260, 'Butter chicken a Creamy, flavorful Indian curry with tender chicken pieces.', 'asian5.jpg', '1350.00', 'Butter Chicken', 1),
(261, 'Italian pasta, renowned worldwide, boasts diverse shapes and textures with delectable sauces.', 'italian1.jpg', '1400.00', 'Pasta', 2),
(262, 'Lasagna: Layered pasta, ricotta, meat, tomato sauce—a classic Italian comfort', 'italian2.jpg', '1950.00', 'Lasagna', 2),
(263, 'Pizza: Thin crust, gooey cheese, tomato sauce—a universally loved delight.', 'italian3.jpg', '2100.00', 'Pizza', 2),
(264, 'Spaghetti: Long, thin pasta strands with savory sauces, a timeless favorite', 'italian4.jpg', '2050.00', 'Spaghetti', 2),
(265, 'Italian risotto: Creamy Arborio rice dish, infused with flavors, delectable comfort', 'italian5.jpg', '1960.00', 'Risotto', 2),
(266, 'Mexican tacos are flavorful street food, featuring seasoned meats, salsa, and traditional toppings in soft corn tortillas.', 'mexican1.jpg', '780.00', 'Taco', 3),
(267, 'Burrito is a rolled Mexican dish with beans, rice, and fillings', 'mexican2.jpg', '1120.00', 'Buritto', 3),
(268, 'Pozole, a traditional Mexican soup, combines hominy, meat, and aromatic spices, creating a rich and flavorful culinary experience', 'mexican3.jpg', '1450.00', 'Pozole', 3),
(269, 'Elote, a Mexican street food, is grilled corn topped with mayo, cheese, chili, and lime, bursting with delicious flavors', 'mexican4.jpg', '1250.00', 'Elote', 3),
(270, 'Burger, a classic comfort food, features a savory grilled patty, fresh veggies, and condiments within a soft bun.', 'american1.jpg', '970.00', 'Ham Burger', 4),
(271, 'Hot dog, a popular fast food, features a grilled or steamed sausage in a sliced bun with toppings.', 'american2.jpg', '450.00', 'Hot Dog', 4),
(272, 'Sandwich, a versatile meal, consists of layers of fillings between slices of bread, offering various flavors and textures.', 'american3.jpg', '350.00', 'Sandwitch', 4),
(273, 'Chicken nuggets, bite-sized delights, are breaded and fried pieces of seasoned chicken, enjoyed as a tasty snack or meal.', 'american4.jpg', '760.00', 'Chicken Nuggets', 4),
(274, 'Steak, a culinary delight, is a flavorful and juicy cut of meat, typically grilled or pan-seared to perfection.', 'american5.jpg', '2160.00', 'Steak', 4),
(275, 'Tarte Tatin, a French dessert, is an upside-down caramelized apple tart with a buttery, flaky crust.', 'french1.jpg', '1380.00', 'Tarte Tatin', 5),
(276, 'French onion soup, a classic dish, features caramelized onions, beef broth, and melted cheese on toasted bread.', 'french2.jpg', '880.00', 'French onion soup', 5),
(277, 'Cassoulet, a French comfort dish, combines white beans, meats like sausage and duck, in a slow-cooked, hearty stew.', 'french3.jpg', '1350.00', 'Cassoulet', 5),
(278, 'Bouillabaisse, a Provencal fish stew, features a rich broth with various fish, shellfish, herbs, and spices.', 'french4.jpg', '1490.00', 'Bouillabaisse', 5),
(279, 'French fries, a beloved side dish, are deep-fried potato sticks, crispy and golden brown, often served with various toppings.', 'french5.jpg', '420.00', 'French Fries', 5),
(280, 'Paneer Butter Masala, a popular Indian dish, features paneer cubes in a rich and creamy tomato-based curry with spices.', 'veg1.jpg', '380.00', 'Butter Paneer ', 6),
(281, 'Mushroom Curry, a flavorful vegetarian dish, showcases mushrooms in a spiced and aromatic curry sauce, perfect with rice or bread.', 'veg2.jpg', '310.00', 'Mushroom Curry', 6),
(282, 'Veg pizza, a delicious vegetarian option, combines a crispy crust with tomato sauce, cheese, and assorted vegetables as toppings.', 'veg3.jpg', '910.00', 'Veg Pizza', 6),
(283, 'Dosa, a South Indian delicacy, is a thin, crispy fermented rice and urad dal crepe, often served with chutney.', 'veg4.jpg', '130.00', 'Dosa', 6),
(284, 'Poori, a popular Indian bread, is a deep-fried, puffed unleavened wheat bread, typically served with savory or sweet accompaniments.', 'veg5.jpg', '160.00', 'Poori', 6);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK1cf90etcu98x1e6n9aks3tel3` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
