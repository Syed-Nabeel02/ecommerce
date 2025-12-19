/**
 * LoginView.jsx
 * User login page with username and password fields.
 * Includes password visibility toggle and form validation.
 */

// React hook for component state
import { useState } from "react";
// React Hook Form for form validation and management
import { useForm } from "react-hook-form";
// Icons for login button and password visibility toggle
import { MdLogin, MdVisibility, MdVisibilityOff } from "react-icons/md";
// React Router for navigation and links
import { Link, useNavigate } from "react-router-dom";
// Reusable text input component with validation
import TextInput from "../../components/ui/forms/TextInput";
// Redux hook to dispatch actions
import { useDispatch } from "react-redux";
// Redux action to authenticate user login
import { authenticateUserLogin } from "../../store/actions";
// Toast notifications for user feedback
import toast from "react-hot-toast";

/**
 * LoginView Component
 * Login page where users enter credentials to access their account
 */
const LoginView = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    // Track loading state during login API call
    const [isLoading, setIsLoading] = useState(false);
    // Track whether password is visible or hidden
    const [showPassword, setShowPassword] = useState(false);

    // Initialize React Hook Form with validation
    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm({
        mode: "onTouched",
        defaultValues: {
            username: "",
            password: "",
        },
    });

    // Submit login form and dispatch authentication action
    const handleLogin = async (formData) => {
        dispatch(authenticateUserLogin(formData, toast, reset, navigate, setIsLoading));
    };

    // Toggle password visibility between text and password type
    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <div className="min-h-[calc(100vh-64px)] bg-gradient-to-br from-slate-50 to-slate-100 flex justify-center items-center p-4">
            <div className="w-full max-w-md">
                {/* Card container */}
                <div className="bg-white shadow-lg rounded-lg overflow-hidden">
                    {/* Header section */}
                    <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 sm:px-8 py-10 flex flex-col items-center justify-center space-y-3">

                        <h1 className="text-white text-center font-montserrat text-2xl sm:text-3xl font-bold">
                            Hello Again
                        </h1>
                        <p className="text-red-100 text-sm text-center">
                            Access your account by signing in
                        </p>
                    </div>

                    {/* Form section */}
                    <form
                        onSubmit={handleSubmit(handleLogin)}
                        className="px-6 sm:px-8 py-8 space-y-5">

                        {/* Username input */}
                        <div>
                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                Username
                            </label>
                            <input
                                type="text"
                                placeholder="Type your username here"
                                disabled={isLoading}
                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                    errors.username
                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                        : "border-slate-200 focus:border-slate-800 bg-slate-50"
                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                {...register("username", {
                                    required: "Please enter your username",
                                    minLength: {
                                        value: 3,
                                        message: "Username should have at least 3 characters",
                                    },
                                })}
                            />
                            {errors.username && (
                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                    <span>•</span> {errors.username.message}
                                </p>
                            )}
                        </div>

                        {/* Password input with toggle */}
                        <div>
                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                Password
                            </label>
                            <div className="relative">
                                <input
                                    type={showPassword ? "text" : "password"}
                                    placeholder="Type your password here"
                                    disabled={isLoading}
                                    className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none pr-10 ${
                                        errors.password
                                            ? "border-red-400 focus:border-red-500 bg-red-50"
                                            : "border-slate-200 focus:border-slate-800 bg-slate-50"
                                    } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                    {...register("password", {
                                        required: "Please enter your password",
                                        minLength: {
                                            value: 6,
                                            message: "Password should have at least 6 characters",
                                        },
                                    })}
                                />
                                <button
                                    type="button"
                                    onClick={togglePasswordVisibility}
                                    disabled={isLoading}
                                    className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed">
                                    {showPassword ? (
                                        <MdVisibilityOff className="text-lg" />
                                    ) : (
                                        <MdVisibility className="text-lg" />
                                    )}
                                </button>
                            </div>
                            {errors.password && (
                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                    <span>•</span> {errors.password.message}
                                </p>
                            )}
                        </div>

                        {/* Forgot password link */}
                        <div className="flex justify-end">
                            <Link
                                to="/forgot-password"
                                className="text-xs font-semibold text-slate-600 hover:text-slate-800 transition-colors">
                                Forgot your password?
                            </Link>
                        </div>

                        {/* Submit button */}
                        <button
                            disabled={isLoading}
                              className="w-full bg-gradient-to-r from-red-600 to-red-700 text-white font-semibold py-2.5 rounded-lg hover:shadow-lg transition-all duration-200 disabled:opacity-70 disabled:cursor-not-allowed flex items-center justify-center gap-2"
                                type="submit">
                            {isLoading ? (
                                <>
                                    <span className="inline-block w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></span>
                                    Logging in...
                                </>
                            ) : (
                                <>
                                    <MdLogin className="text-lg" />
                                    Log In
                                </>
                            )}
                        </button>

                        {/* Divider */}
                        <div className="flex items-center gap-3 my-6">
                            <div className="flex-1 h-px bg-slate-200"></div>
                            <span className="text-xs text-slate-500 font-medium">First time here?</span>
                            <div className="flex-1 h-px bg-slate-200"></div>
                        </div>

                        {/* Sign up link */}
                        <p className="text-center text-sm text-slate-600">
                            No account yet?
                            <Link
                                to="/register"
                                className="font-semibold text-slate-800 hover:text-slate-900 underline transition-colors ml-1">
                                Sign up now
                            </Link>
                        </p>
                    </form>
                </div>


            </div>
        </div>
    );
};

export default LoginView;
