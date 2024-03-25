-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 25, 2024 at 01:15 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_mahasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL,
  `nim` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `jalur_masuk` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `nama`, `jenis_kelamin`, `jalur_masuk`, `status`) VALUES
(1, '2203999', 'Amelia Zalfa Julianti', 'Perempuan', 'SNMPTN', 'Aktif'),
(2, '2202292', 'Muhammad Iqbal Fadhilah', 'Laki-laki', 'SBMPTN', 'Aktif'),
(3, '2202346', 'Muhammad Rifky Afandi', 'Laki-laki', 'Mandiri', 'Aktif'),
(4, '2210239', 'Muhammad Hanif Abdillah', 'Laki-laki', 'SNMPTN', 'Aktif'),
(5, '2202046', 'Nurainun', 'Perempuan', 'SBMPTN', 'Cuti'),
(6, '2205101', 'Kelvin Julian Putra', 'Laki-laki', 'Mandiri', 'Aktif'),
(7, '2200163', 'Rifanny Lysara Annastasya', 'Perempuan', 'SNMPTN', 'Aktif'),
(8, '2202869', 'Revana Faliha Salma', 'Perempuan', 'SBMPTN', 'Aktif'),
(9, '2209489', 'Rakha Dhifiargo Hariadi', 'Laki-laki', 'Mandiri', 'Cuti'),
(10, '2203142', 'Roshan Syalwan Nurilham', 'Laki-laki', 'SNMPTN', 'Aktif'),
(11, '2200311', 'Raden Rahman Ismail', 'Laki-laki', 'SBMPTN', 'Aktif'),
(12, '2200978', 'Ratu Syahirah Khairunnisa', 'Perempuan', 'Mandiri', 'Aktif'),
(13, '2204509', 'Muhammad Fahreza Fauzan', 'Laki-laki', 'SNMPTN', 'Cuti'),
(14, '2205027', 'Muhammad Rizki Revandi', 'Laki-laki', 'SBMPTN', 'Aktif'),
(15, '2203484', 'Arya Aydin Margono', 'Laki-laki', 'Mandiri', 'Aktif'),
(16, '2200481', 'Marvel Ravindra Dioputra', 'Laki-laki', 'SNMPTN', 'Aktif'),
(17, '2209889', 'Muhammad Fadlul Hafiizh', 'Laki-laki', 'SBMPTN', 'Aktif'),
(18, '2206697', 'Rifa Sania', 'Perempuan', 'Mandiri', 'Aktif'),
(19, '2207260', 'Imam Chalish Rafidhul Haque', 'Laki-laki', 'SNMPTN', 'Aktif'),
(20, '2204343', 'Meiva Labibah Putri', 'Perempuan', 'SBMPTN', 'Aktif'),
(25, '2201718', 'Muhamad Tio Ariyanto', 'Laki-laki', 'SNMPTN', 'Aktif');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
