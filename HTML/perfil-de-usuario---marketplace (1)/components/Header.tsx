import React from 'react';
import { Search, User, Menu, ShoppingCart } from 'lucide-react';

const Header: React.FC = () => {
  return (
    <header className="w-full bg-[#1a1b41] py-4 px-6 shadow-md text-white">
      <div className="container mx-auto flex items-center justify-between gap-4">
        {/* Logo Placeholder / Menu */}
        <div className="flex items-center gap-4">
          <div className="bg-pink-100 text-[#1a1b41] p-2 rounded-full">
             <Menu size={24} />
          </div>
        </div>

        {/* Search Bar */}
        <div className="flex-1 max-w-2xl relative">
          <input 
            type="text" 
            placeholder="Buscar productos, marcas y más..." 
            className="w-full py-2 px-4 pr-10 rounded-full text-gray-800 focus:outline-none focus:ring-2 focus:ring-pink-300"
          />
          <Search className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
        </div>

        {/* Actions */}
        <div className="flex items-center gap-4 text-sm font-medium">
           <div className="hidden md:flex gap-2">
              <button className="px-4 py-1 bg-pink-100 text-[#1a1b41] rounded-full hover:bg-pink-200 transition">
                Crear cuenta
              </button>
              <button className="px-4 py-1 border border-pink-100 text-pink-100 rounded-full hover:bg-[#2a2b55] transition">
                Ingresar
              </button>
           </div>
           <div className="bg-pink-100 text-[#1a1b41] p-2 rounded-full">
              <User size={24} />
           </div>
        </div>
      </div>
    </header>
  );
};

export default Header;