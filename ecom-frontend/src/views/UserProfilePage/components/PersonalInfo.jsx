/**
 * PersonalInfo.jsx
 *
 * Component for displaying user personal information.
 * Shows read-only user data (username and email).
 *
 * USED IN:
 * - ProfileView (User profile page)
 *
 * KEY FEATURES:
 * - Display user information (both email and username are read-only)
 *
 * @param {Object} user - User object containing email and username
 */

const PersonalInfo = ({ user }) => {
  return (
    <div className="p-6 border rounded-lg shadow-sm bg-white">
      <h2 className="text-2xl font-semibold mb-4 text-slate-800">
        Personal Information
      </h2>

      <div className="space-y-4">
        {/* Username - Read-only */}
        <div>
          <label className="block text-sm font-semibold text-slate-700 mb-1">
            Username
          </label>
          <p className="text-slate-800 font-medium">{user?.username}</p>
        </div>

        {/* Email - Read-only */}
        <div>
          <label className="block text-sm font-semibold text-slate-700 mb-1">
            Email
          </label>
          <p className="text-slate-600">{user?.email}</p>
        </div>
      </div>
    </div>
  );
};

export default PersonalInfo;
