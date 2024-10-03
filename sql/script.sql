create database Bookshelf

go

use Bookshelf

create table NguoiDung
(
	TaiKhoan varchar(50) primary key,
	MatKhau varchar(64) not null,
	Ten nvarchar(50) not null,
	Email nvarchar(50) not null,
	NguoiTheoDoi int default 0 not null check(NguoiTheoDoi >= 0),
	DangTheoDoi int default 0 not null check(DangTheoDoi >= 0)
)

create table ChuyenMuc
(
	ID int identity primary key,
	TenChuyenMuc nvarchar(50) not null,
)


create table Sach
(
	ID int identity primary key,
	NguoiDung varchar(50) references NguoiDung(TaiKhoan) not null,
	ChuyenMuc int references ChuyenMuc(ID) not null,
	Ten nvarchar(100) not null,
	SoTrangChuong int check(SoTrangChuong > 0) not null,
	DaDoc int default 0 not null,
	constraint Check_DaDoc check(DaDoc >= 0 and DaDoc <= SoTrangChuong),
	MoTa nvarchar(500),
	AnhBia varbinary(max) not null,
	GhiChu nvarchar(100),
)

create table TheoDoi
(
	NguoiTheoDoi varchar(50) references NguoiDung(TaiKhoan) not null,
	TheoDoi varchar(50) references NguoiDung(TaiKhoan) not null,
	primary key(NguoiTheoDoi, TheoDoi)
)

