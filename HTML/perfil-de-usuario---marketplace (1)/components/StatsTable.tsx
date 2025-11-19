import React from 'react';
import { UserLevel } from '../types';

interface StatsTableProps {
  level: UserLevel;
  reputation: number;
  count: number;
}

const StatsTable: React.FC<StatsTableProps> = ({ level, reputation, count }) => {
  
  // Determine color based on level (mockup shows orange for Bronze)
  const getLevelColor = (l: UserLevel) => {
    switch (l) {
      case UserLevel.GOLD: return 'bg-yellow-400 text-yellow-900';
      case UserLevel.SILVER: return 'bg-gray-300 text-gray-800';
      case UserLevel.BRONZE: return 'bg-orange-400 text-[#1a1b41]';
      default: return 'bg-gray-200';
    }
  };

  return (
    <div className="flex flex-col gap-3 w-full max-w-md">
      {/* Level Row */}
      <div className="grid grid-cols-2 gap-4">
        <div className="bg-[#e6c4c4] bg-opacity-60 p-3 flex items-center justify-center font-bold text-[#1a1b41] rounded shadow-sm">
          Nivel:
        </div>
        <div className={`${getLevelColor(level)} p-3 flex items-center justify-center font-bold uppercase tracking-wide rounded shadow-sm`}>
          {level}
        </div>
      </div>

      {/* Reputation Row */}
      <div className="grid grid-cols-2 gap-4">
        <div className="bg-[#e6c4c4] bg-opacity-60 p-3 flex items-center justify-center font-bold text-[#1a1b41] rounded shadow-sm">
          Reputación:
        </div>
        <div className="bg-[#dcbaba] bg-opacity-40 p-3 flex items-center justify-center font-medium text-gray-700 rounded shadow-sm">
          {reputation} pts.
        </div>
      </div>

      {/* Count Row */}
      <div className="grid grid-cols-2 gap-4">
        <div className="bg-[#e6c4c4] bg-opacity-60 p-3 flex items-center justify-center font-bold text-[#1a1b41] rounded shadow-sm">
          Cantidad pub.
        </div>
        <div className="bg-[#dcbaba] bg-opacity-40 p-3 flex items-center justify-center font-medium text-gray-700 rounded shadow-sm">
          {count}
        </div>
      </div>
    </div>
  );
};

export default StatsTable;