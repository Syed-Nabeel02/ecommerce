import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import toast from "react-hot-toast";
import TextInput from "../../../../components/ui/forms/TextInput";
import {
  addNewCategoryDashboard,
  modifyDashboardCategory,
} from "../../../../store/actions";

const AddCategoryForm = ({ setOpen, open, category, update = false, navigate, pathname }) => {
  const dispatch = useDispatch();
  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm({
    mode: "onTouched",
  });

  const handleCategorySubmit = (formData) => {
    if (!update) {
      dispatch(addNewCategoryDashboard(formData, setOpen, reset, toast));
    } else {
      dispatch(
        modifyDashboardCategory(formData, setOpen, category.id, reset, toast, navigate, pathname)
      );
    }
  };

  useEffect(() => {
    if (update && category?.categoryName) {
      setValue("categoryName", category.categoryName);
    }
  }, [update, category, setValue]);

  const closeModal = () => setOpen(false);
  const submitButtonLabel = open ? "Loading..." : update ? "Update" : "Save";

  return (
    <div className="py-6 relative h-full">
      <form className="space-y-6" onSubmit={handleSubmit(handleCategorySubmit)}>
        <TextInput
          label="Category Name"
          required
          id="categoryName"
          type="text"
          message="This field is required*"
          placeholder="Enter category name"
          register={register}
          errors={errors}
        />

        <div className="flex w-full justify-between items-center absolute bottom-6 left-0 right-0 px-8">
          <button
            disabled={open}
            onClick={closeModal}
            type="button"
            className="border border-gray-300 rounded-lg font-medium text-gray-700 py-2 px-6 text-sm hover:bg-gray-50 transition-colors duration-200"
          >
            Cancel
          </button>
          <button
            disabled={open}
            type="submit"
            className="bg-red-600 hover:bg-red-700 disabled:bg-red-400 text-white rounded-lg font-medium py-2 px-6 text-sm transition-colors duration-200"
          >
            {submitButtonLabel}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddCategoryForm;
