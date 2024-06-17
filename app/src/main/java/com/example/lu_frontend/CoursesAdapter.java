package com.example.lu_frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lu_frontend.apiIntegration.dto.Course;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    private List<Course> courses;

    public CoursesAdapter(List<Course> courses) {
        this.courses = courses;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView courseNameTextView, courseIdTextView;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseName);
            courseIdTextView = itemView.findViewById(R.id.courseId);
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.courseNameTextView.setText(course.getCourseName());
        holder.courseIdTextView.setText(String.valueOf(course.getCourseId()));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void updateCourses(List<Course> newCourses) {
        courses = newCourses;
        notifyDataSetChanged();
    }
}
